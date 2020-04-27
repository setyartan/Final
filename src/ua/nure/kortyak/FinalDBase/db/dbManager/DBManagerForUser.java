package ua.nure.kortyak.FinalDBase.db.dbManager;

import org.apache.log4j.Logger;

import ua.nure.kortyak.FinalDBase.db.finalClasses.Fields;
import ua.nure.kortyak.FinalDBase.db.finalClasses.SQLQueries;
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
 * DB manager for user. Works with Apache Derby DB. Only the required DAO methods are
 * defined!
 *
 * @author E.Kortyak
 */
public class DBManagerForUser{

    private static final Logger LOG = Logger.getLogger(DBManagerForUser.class);

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

    private static DBManagerForUser instance;

    public static synchronized DBManagerForUser getInstance(){
        if (instance == null) {
            instance = new DBManagerForUser();
        }
        return instance;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Main user methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns a user with the given identifier.
     *
     * @param id User identifier.
     * @return User entity.
     * @throws DBException can be thrown.
     */
    public User findUser(long id) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_USER_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
        } finally {
            DBUtils.close(con, pstmt, rs);
        }
        return user;
    }

    /**
     * Returns a user with the given login.
     *
     * @param login User login.
     * @return User entity.
     * @throws DBException can be thrown.
     */
    public User findUserByLogin(String login) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            DBUtils.close(con, pstmt, rs);
        }
        return user;
    }

    /**
     * Returns a list of users.
     *
     * @return list of users.
     * @throws DBException can be thrown.
     */
    public List<User> findAllUsers() throws DBException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_FIND_ALL_USERS);
            while (rs.next()) {
                users.add(extractUser(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_USERS, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_USERS, ex);
        } finally {
            DBUtils.close(con);
        }

        return users;
    }


    /**
     * Update user.
     *
     * @param user user to update.
     * @throws DBException can be thrown.
     */
    public void updateUser(User user) throws DBException {
        Connection con = null;
        try {
            con = utils.getConnection();
            updateUser(con, user);
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    /**
     * Insert user.
     *
     * @param user user to insert.
     * @throws DBException can be thrown.
     */
    public void insertUser(User user) throws DBException {
        Connection con = null;
        PreparedStatement pStatement;
        ResultSet rs = null;
        try {
            con = utils.getConnection();
            pStatement = con.prepareStatement(
                    SQLQueries.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pStatement.setString(k++, user.getLogin());
            pStatement.setString(k++, user.getPassword());
            pStatement.setString(k++, user.getFirstName());
            pStatement.setString(k++, user.getLastName());
            pStatement.setInt(k++, user.getRoleId());
            pStatement.setLong(k, user.getAccount());
            if (pStatement.executeUpdate() > 0) {
                rs = pStatement.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    user.setId((long) userId);
                }
            }
            if (rs != null && rs.next()) {
                extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    /**
     * Delete user.
     *
     * @param user user to delete.
     * @throws DBException can be thrown.
     */
    public void deleteUser(User user) throws DBException {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_DELETE_USER);
            int k = 1;
            pstmt.setLong(k, user.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_DELETE_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_USER, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    /**
     * Block user.
     *
     * @param user user to block.
     * @throws DBException can be thrown.
     */
    public void blockUser(User user) throws DBException {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_BLOCK_USER);
            int k = 1;
            pstmt.setLong(k, user.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_BLOCK_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_BLOCK_USER, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    /**
     * Returns a list of blocked users.
     *
     * @return list of blocked users.
     * @throws DBException can be thrown.
     */
    public List<User> findAllBlockedUsers() throws DBException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_FIND_ALL_BLOCKED_USERS);
            while (rs.next()) {
                int k = 1;
                users.add(findUser(rs.getLong(k)));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_ALL_BLOCKED_USERS, ex);
            throw new DBException(Messages.ERR_CANNOT_ALL_BLOCKED_USERS, ex);
        } finally {
            DBUtils.close(con);
        }
        return users;
    }

    /**
     * Unblock user.
     *
     * @param user user to unblock.
     * @throws DBException can be thrown.
     */
    public void unblockUser(User user) throws DBException {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_UNBLOCK_USER);
            int k = 1;
            pstmt.setLong(k, user.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_UNBLOCK_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_UNBLOCK_USER, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    // //////////////////////////////////////////////////////////
    // Entity access methods (for transactions)
    // //////////////////////////////////////////////////////////

    /**
     * Update user.
     *
     * @param user user to update.
     * @throws DBException can be thrown.
     */
    private void updateUser(Connection con, User user) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQLQueries.SQL_UPDATE_USER);
            int k = 1;
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getFirstName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setLong(k++, user.getAccount());
            pstmt.setLong(k, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_UPDATE_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
        } finally {
            DBUtils.close(pstmt);
        }
    }

    // //////////////////////////////////////////////////////////
    // Other methods
    // //////////////////////////////////////////////////////////

    /**
     * Extracts a user entity from the result set.
     *
     * @param rs Result set from which a user entity will be extracted.
     * @return User entity
     */
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.ENTITY_ID));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs.getString(Fields.USER_LAST_NAME));
        user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
        user.setAccount(rs.getLong(Fields.USER_ACCOUNT));
        return user;
    }
}
