package ua.nure.kortyak.FinalDBase.db.dbManager;

import org.apache.log4j.Logger;

import ua.nure.kortyak.FinalDBase.db.finalClasses.Fields;
import ua.nure.kortyak.FinalDBase.db.finalClasses.SQLQueries;
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
 * DB manager for theme. Works with Apache Derby DB. Only the required DAO methods are
 * defined!
 *
 * @author E.Kortyak
 */
public class DBManagerForThemes{

    private static final Logger LOG = Logger.getLogger(DBManagerForThemes.class);

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

    private static DBManagerForThemes instance;

    public static synchronized DBManagerForThemes getInstance(){
        if (instance == null) {
            instance = new DBManagerForThemes();
        }
        return instance;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Main theme methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns a theme with the given identifier.
     *
     * @param id Theme identifier.
     * @return Theme entity.
     * @throws DBException can be thrown.
     */
    public Theme findTheme(long id) throws DBException {
        Theme theme = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_THEME_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                theme = extractTheme(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_THEME_BY_ID, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_THEME_BY_ID, ex);
        } finally {
            DBUtils.close(con, pstmt, rs);
        }
        return theme;
    }


    /**
     * Returns a theme with the given name.
     *
     * @param name Theme name.
     * @return Theme entity.
     * @throws DBException can be thrown.
     */
    public Theme findThemeByName(String name) throws DBException {
        Theme theme = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_THEME_BY_NAME);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                theme = extractTheme(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_THEME_BY_LOGIN, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_THEME_BY_LOGIN, ex);
        } finally {
            DBUtils.close(con, pstmt, rs);
        }
        return theme;
    }

    /**
     * Returns a list of themes.
     *
     * @return list of themes.
     * @throws DBException can be thrown.
     */
    public List<Theme> findAllThemes() throws DBException {
        List<Theme> themes = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_FIND_ALL_THEMES);
            while (rs.next()) {
                themes.add(extractTheme(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_THEMES, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_THEMES, ex);
        } finally {
            DBUtils.close(con);
        }
        return themes;
    }


    /**
     * Update theme.
     *
     * @param theme theme to update.
     * @throws DBException can be thrown.
     */
    public void updateTheme(Theme theme) throws DBException {
        Connection con = null;
        try {
            con = utils.getConnection();
            updateTheme(con, theme);
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_THEME, ex);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_THEME, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    /**
     * Insert theme.
     *
     * @param theme theme to insert.
     * @throws DBException can be thrown.
     */
    public void insertTheme(Theme theme) throws DBException {
        Connection con = null;
        PreparedStatement pStatement;
        ResultSet rs = null;
        try {
            con = utils.getConnection();
            pStatement = con.prepareStatement(
                    SQLQueries.SQL_INSERT_THEME, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pStatement.setString(k, theme.getName());
            if (pStatement.executeUpdate() > 0) {
                rs = pStatement.getGeneratedKeys();
                if (rs.next()) {
                    int themeId = rs.getInt(1);
                    theme.setId((long) themeId);
                }
            }
            if (rs != null && rs.next()) {
                extractTheme(rs);
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
     * Delete theme.
     *
     * @param theme theme to delete.
     * @throws DBException can be thrown.
     */
    public void deleteTheme(Theme theme) throws DBException {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_DELETE_THEME);
            int k = 1;
            pstmt.setLong(k, theme.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_DELETE_THEME, ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_THEME, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    // //////////////////////////////////////////////////////////
    // Entity access methods (for transactions)
    // //////////////////////////////////////////////////////////

    /**
     * Update theme.
     *
     * @param theme theme to update.
     * @throws DBException can be thrown.
     */
    private void updateTheme(Connection con, Theme theme) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQLQueries.SQL_UPDATE_THEME);
            int k = 1;
            pstmt.setString(k++, theme.getName());
            pstmt.setLong(k, theme.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_UPDATE_THEME, ex);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_THEME, ex);
        } finally {
            DBUtils.close(pstmt);
        }
    }

    // //////////////////////////////////////////////////////////
    // Other methods
    // //////////////////////////////////////////////////////////

    /**
     * Extracts a theme entity from the result set.
     *
     * @param rs Result set from which a theme entity will be extracted.
     * @return Theme entity
     */
    Theme extractTheme(ResultSet rs) throws SQLException {
        Theme theme = new Theme();
        theme.setId(rs.getLong(Fields.ENTITY_ID));
        theme.setName(rs.getString(Fields.THEME_NAME));
        return theme;
    }

}
