package ua.nure.kortyak.FinalDBase.db.dbManager;

import org.apache.log4j.Logger;

import ua.nure.kortyak.FinalDBase.db.finalClasses.SQLQueries;
import ua.nure.kortyak.FinalDBase.db.entity.Periodical;
import ua.nure.kortyak.FinalDBase.db.entity.Theme;
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
 * DB manager for periodical of theme.
 * Works with Apache Derby DB. Only the required DAO methods are defined!
 *
 * @author E.Kortyak
 */
public class DBManagerPOT {

    private static final Logger LOG = Logger.getLogger(DBManagerPOT.class);

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

    private static DBManagerPOT instance;

    public static synchronized DBManagerPOT getInstance(){
        if (instance == null) {
            instance = new DBManagerPOT();
        }
        return instance;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods on cooperation between periodicals and themes
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Insert periodical for theme.
     *
     * @param theme       theme to which to insert periodical.
     * @param periodicals periodicals to insert.
     * @throws DBException can be thrown.
     */
    public void insertPeriodicalsForTheme(Theme theme, Periodical... periodicals) throws DBException {
        if (periodicals.length == 0) {
            return;
        }
        Connection con = null;
        PreparedStatement pStatement;
        try {
            con = utils.getConnection();
            pStatement = con.prepareStatement(
                    SQLQueries.SQL_INSERT_PERIODICAL_FOR_THEME, Statement.RETURN_GENERATED_KEYS);
            for (Periodical periodical : periodicals) {
                pStatement.setLong(1, theme.getId());
                pStatement.setLong(2, periodical.getId());
                pStatement.executeUpdate();
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_INSERT_PERIODICAL_FOR_THEME, ex);
            throw new DBException(Messages.ERR_CANNOT_INSERT_PERIODICAL_FOR_THEME, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    /**
     * Returns a list of periodicals of theme.
     *
     * @param theme theme periodicals to find.
     * @return list of periodicals of theme.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalOfTheme(Theme theme) throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_ALL_PERIODICALS_OF_THEME);
            int k = 1;
            pstmt.setLong(k, theme.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                periodicals.add(DBManagerForPeriodicals.getInstance()
                        .findPeriodical(rs.getLong("periodical_id")));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_FIND_ALL_PERIODICALS_OF_THEME, ex);
            throw new DBException(Messages.ERR_CANNOT_FIND_ALL_PERIODICALS_OF_THEME, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals of theme which were sorted before.
     *
     * @param theme theme periodicals to find.
     * @return list of periodicals of theme which were sorted before.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalOfThemeWithSorting(List<Periodical> periodicals, Theme theme)
            throws DBException {
        List<Periodical> periodicalsOfTheme = new ArrayList<>();
        for (Periodical periodical : periodicals) {
            for (Periodical periodicalOfTheme : findAllPeriodicalOfTheme(theme)) {
                if (periodicalOfTheme.equals(periodical)) {
                    periodicalsOfTheme.add(periodical);
                }
            }
        }
        return periodicalsOfTheme;
    }

    /**
     * Delete periodical of theme.
     *
     * @param periodical periodical to delete.
     * @param theme      theme's periodical to be deleted.
     * @throws DBException can be thrown.
     */
    public void deletePeriodicalOfTheme(Periodical periodical, Theme theme) throws DBException {
        Connection con = null;
        PreparedStatement pStatement;
        try {
            con = utils.getConnection();
            pStatement = con.prepareStatement(SQLQueries.SQL_DELETE_PERIODICAL_FROM_THEME);
            int k = 1;
            pStatement.setLong(k++, periodical.getId());
            pStatement.setLong(k, theme.getId());
            pStatement.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_DELETE_PERIODICAL_FROM_THEME, ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_PERIODICAL_FROM_THEME, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    /**
     * Find theme of periodical.
     *
     * @param periodical periodical theme to find.
     * @throws DBException can be thrown.
     */
    public long findThemeOfPeriodical(Periodical periodical) throws DBException {
        long idOfTheme = -1;
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_THEME_OF_PERIODICAL);
            int k = 1;
            pstmt.setLong(k, periodical.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                idOfTheme = rs.getLong(1);
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_FIND_THEME_OF_PERIODICAL, ex);
            throw new DBException(Messages.ERR_CANNOT_FIND_THEME_OF_PERIODICAL, ex);
        } finally {
            DBUtils.close(con);
        }
        return idOfTheme;
    }
}
