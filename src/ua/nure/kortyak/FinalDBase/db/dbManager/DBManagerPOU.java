package ua.nure.kortyak.FinalDBase.db.dbManager;

import org.apache.log4j.Logger;

import ua.nure.kortyak.FinalDBase.db.finalClasses.SQLQueries;
import ua.nure.kortyak.FinalDBase.db.entity.Periodical;
import ua.nure.kortyak.FinalDBase.db.entity.User;
import ua.nure.kortyak.FinalDBase.exception.DBException;
import ua.nure.kortyak.FinalDBase.exception.Messages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DB manager for periodical of user.
 * Works with Apache Derby DB. Only the required DAO methods are defined!
 *
 * @author E.Kortyak
 */
public class DBManagerPOU {

    private static final Logger LOG = Logger.getLogger(DBManagerPOU.class);

    private static DBUtils utils;

    static {
        try {
            utils = new DBUtils();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    // //////////////////////////////////////////////////////////
    // singleton
    // //////////////////////////////////////////////////////////

    private static DBManagerPOU instance;

    public static synchronized DBManagerPOU getInstance(){
        if (instance == null) {
            instance = new DBManagerPOU();
        }
        return instance;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods on cooperation between user and periodicals
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Insert periodical for user.
     *
     * @param user        user to whom to insert periodical.
     * @param periodicals periodicals to insert.
     * @throws DBException can be thrown.
     */
    public void insertPeriodicalsForUser(User user, Periodical... periodicals) throws DBException {
        if (periodicals.length == 0) {
            return;
        }
        Connection con = null;
        PreparedStatement pStatement;
        try {
            con = utils.getConnection();
            pStatement = con.prepareStatement(
                    SQLQueries.SQL_INSERT_PERIODICAL_FOR_USER, Statement.RETURN_GENERATED_KEYS);
            for (Periodical periodical : periodicals) {
                pStatement.setLong(1, user.getId());
                pStatement.setLong(2, periodical.getId());
                pStatement.executeUpdate();
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_INSERT_PERIODICAL_FOR_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_INSERT_PERIODICAL_FOR_USER, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    /**
     * Returns a list of periodicals of user.
     *
     * @param user user periodicals to find.
     * @return list of periodicals of user.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalOfUser(User user) throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_ALL_PERIODICALS_OF_USER);
            int k = 1;
            pstmt.setLong(k, user.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                periodicals.add(DBManagerForPeriodicals.getInstance()
                        .findPeriodical(rs.getLong("periodical_id")));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_FIND_ALL_PERIODICALS_OF_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_FIND_ALL_PERIODICALS_OF_USER, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Delete periodical of user.
     *
     * @param periodical periodical to delete.
     * @param user       user's periodical to be deleted.
     * @throws DBException can be thrown.
     */
    public void deletePeriodicalOfUser(Periodical periodical, User user) throws DBException {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_DELETE_PERIODICAL_FROM_USER);
            int k = 1;
            pstmt.setLong(k++, periodical.getId());
            pstmt.setLong(k, user.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_DELETE_PERIODICAL_FROM_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_PERIODICAL_FROM_USER, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    /**
     * Returns a list of users of periodical.
     *
     * @param periodical periodical to delete.
     * @throws DBException can be thrown.
     */
    public List<User> findAllUsersOfPeriodical(Periodical periodical) throws DBException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_ALL_USERS_OF_PERIODICAL);
            int k = 1;
            pstmt.setLong(k, periodical.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(DBManagerForUser.getInstance()
                        .findUser(rs.getLong("user_id")));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_FIND_ALL_USERS_OF_PERIODICAL, ex);
            throw new DBException(Messages.ERR_CANNOT_FIND_ALL_USERS_OF_PERIODICAL, ex);
        } finally {
            DBUtils.close(con);
        }
        return users;
    }
}
