package ua.nure.kortyak.FinalDBase.db.dbManager;

import org.apache.log4j.Logger;

import ua.nure.kortyak.FinalDBase.exception.DBException;
import ua.nure.kortyak.FinalDBase.exception.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Holder for util commands of DB tables and beans.
 *
 * @author E.Kortyak
 */
public class DBUtils {

    private static final Logger LOG = Logger.getLogger(DBUtils.class);

    DBUtils() throws DBException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            // dbase - the name of data source
            ds = (DataSource) envContext.lookup("jdbc/dbase");
            LOG.trace("Data source ==> " + ds);
        } catch (NamingException | ClassNotFoundException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    private DataSource ds;

    /**
     * Returns a DB connection from the Pool Connections. The Connections Pool is in
     * WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return DB connection.
     */
    public Connection getConnection() throws DBException {
        Connection con;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }

    /**
     * Closes a connection.
     *
     * @param con Connection to be closed.
     */
    public static void close(Connection con) throws DBException {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
                throw new DBException(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    /**
     * Closes a statement object.
     */
    public static void close(Statement stmt) throws DBException {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
                throw new DBException(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    /**
     * Closes a result set object.
     */
    public static void close(ResultSet rs) throws DBException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
                throw new DBException(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }

    /**
     * Closes resources.
     */
    public static void close(Connection con, Statement stmt, ResultSet rs) throws DBException {
        close(rs);
        close(stmt);
        close(con);
    }

    /**
     * Rollbacks a connection.
     *
     * @param con Connection to be rollbacked.
     */
    public static void rollback(Connection con) throws DBException {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
                throw new DBException(Messages.ERR_CANNOT_ROLLBACK, ex);
            }
        }
    }
}
