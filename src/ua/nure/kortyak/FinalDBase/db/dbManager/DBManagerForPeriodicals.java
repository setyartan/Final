package ua.nure.kortyak.FinalDBase.db.dbManager;

import org.apache.log4j.Logger;
import ua.nure.kortyak.FinalDBase.db.finalClasses.Fields;
import ua.nure.kortyak.FinalDBase.db.finalClasses.SQLQueries;
import ua.nure.kortyak.FinalDBase.db.entity.Periodical;
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
 * DB manager for periodical. Works with Apache Derby DB. Only the required DAO methods are
 * defined!
 *
 * @author E.Kortyak
 */
public class DBManagerForPeriodicals{

    private static final Logger LOG = Logger.getLogger(DBManagerForPeriodicals.class);

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

    private static DBManagerForPeriodicals instance;

    public static synchronized DBManagerForPeriodicals getInstance(){
        if (instance == null) {
            instance = new DBManagerForPeriodicals();
        }
        return instance;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Main periodical methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns a periodical with the given identifier.
     *
     * @param id Periodical identifier.
     * @return Periodical entity.
     * @throws DBException can be thrown.
     */
    public Periodical findPeriodical(long id) throws DBException {
        Periodical periodical = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_PERIODICAL_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                periodical = extractPeriodical(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_PERIODICAL_BY_ID, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_PERIODICAL_BY_ID, ex);
        } finally {
            DBUtils.close(con, pstmt, rs);
        }
        return periodical;
    }


    /**
     * Returns a periodical with the given name.
     *
     * @param name Periodical name.
     * @return Periodical entity.
     * @throws DBException can be thrown.
     */
    public Periodical findPeriodicalByName(String name) throws DBException {
        Periodical periodical = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_PERIODICAL_BY_NAME);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                periodical = extractPeriodical(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_PERIODICAL_BY_NAME, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_PERIODICAL_BY_NAME, ex);
        } finally {
            DBUtils.close(con, pstmt, rs);
        }
        return periodical;
    }

    /**
     * Returns a list of periodicals.
     *
     * @return list of periodicals.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodical() throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_FIND_ALL_PERIODICALS);
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Update periodical.
     *
     * @param periodical periodical to update.
     * @throws DBException can be thrown.
     */
    public void updatePeriodical(Periodical periodical) throws DBException {
        Connection con = null;
        try {
            con = utils.getConnection();
            updatePeriodical(con, periodical);
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_PERIODICAL, ex);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_PERIODICAL, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    /**
     * Insert periodical.
     *
     * @param periodical periodical to insert.
     * @throws DBException can be thrown.
     */
    public void insertPeriodical(Periodical periodical) throws DBException {
        Connection con = null;
        PreparedStatement pStatement;
        ResultSet rs = null;
        try {
            con = utils.getConnection();
            pStatement = con.prepareStatement(
                    SQLQueries.SQL_INSERT_PERIODICAL, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pStatement.setString(k++, periodical.getName());
            pStatement.setLong(k, periodical.getPrice());
            if (pStatement.executeUpdate() > 0) {
                rs = pStatement.getGeneratedKeys();
                if (rs.next()) {
                    int periodicalId = rs.getInt(1);
                    periodical.setId((long) periodicalId);
                }
            }
            if (rs != null && rs.next()) {
                extractPeriodical(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_PERIODICAL, ex);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_PERIODICAL, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    /**
     * Delete periodical.
     *
     * @param periodical periodical to delete.
     * @throws DBException can be thrown.
     */
    public void deletePeriodical(Periodical periodical) throws DBException {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_DELETE_PERIODICAL);
            int k = 1;
            pstmt.setLong(k, periodical.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_DELETE_PERIODICAL, ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_PERIODICAL, ex);
        } finally {
            DBUtils.close(con);
        }
    }

    // //////////////////////////////////////////////////////////
    // Entity access methods (for transactions)
    // //////////////////////////////////////////////////////////

    /**
     * Update periodical.
     *
     * @param periodical periodical to update.
     * @throws DBException can be thrown.
     */
    private void updatePeriodical(Connection con, Periodical periodical) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQLQueries.SQL_UPDATE_PERIODICAL);
            int k = 1;
            pstmt.setString(k++, periodical.getName());
            pstmt.setLong(k++, periodical.getPrice());
            pstmt.setLong(k, periodical.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_UPDATE_PERIODICAL, ex);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_PERIODICAL, ex);
        } finally {
            DBUtils.close(pstmt);
        }
    }

    // //////////////////////////////////////////////////////////
    // Other methods
    // //////////////////////////////////////////////////////////

    /**
     * Extracts a periodical entity from the result set.
     *
     * @param rs Result set from which a periodical entity will be extracted.
     * @return Periodical entity
     */
    private Periodical extractPeriodical(ResultSet rs) throws SQLException {
        Periodical periodical = new Periodical();
        periodical.setId(rs.getLong(Fields.ENTITY_ID));
        periodical.setName(rs.getString(Fields.PERIODICAL_NAME));
        periodical.setPrice(rs.getLong(Fields.PERIODICAL_PRICE));
        return periodical;
    }

    // //////////////////////////////////////////////////////////
    // Methods of sorting of periodicals
    // //////////////////////////////////////////////////////////

    /**
     * Returns a list of periodicals sorted by name ASC.
     *
     * @return list of periodicals sorted by name ASC.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalSortedByNameASC() throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_FIND_ALL_PERIODICAL_NAME_ORDER_BY_ASC);
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_ASC, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_ASC, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals sorted by name DESC.
     *
     * @return list of periodicals sorted by name DESC.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalSortedByNameDESC() throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_FIND_ALL_PERIODICAL_NAME_ORDER_BY_DESC);
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_DESC, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_DESC, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals sorted by price ASC.
     *
     * @return list of periodicals sorted by price ASC.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalSortedByPriceASC() throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_FIND_ALL_PERIODICAL_PRICE_ORDER_BY_ASC);
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_PRICE_ASC, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_PRICE_ASC, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals sorted by price DESC.
     *
     * @return list of periodicals sorted by price DESC.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalSortedByPriceDESC() throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_FIND_ALL_PERIODICAL_PRICE_ORDER_BY_DESC);
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_PRICE_DESC, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_PRICE_DESC, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals sorted by name and price ASC.
     *
     * @return list of periodicals sorted  name and by price ASC.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalSortedByNameAndPriceASC() throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_FIND_ALL_PERIODICAL_NAME_AND_PRICE_ORDER_BY_ASC);
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_AND_PRICE_ASC, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_AND_PRICE_ASC, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals sorted  name and by price DESC.
     *
     * @return list of periodicals sorted name and by price DESC.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalSortedByNameAndPriceDESC() throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_FIND_ALL_PERIODICAL_NAME_AND_PRICE_ORDER_BY_DESC);
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_AND_PRICE_DESC, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_AND_PRICE_DESC, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals sorted by name DESC and price ASC.
     *
     * @return list of periodicals sorted by name DESC and price ASC.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalSortedByNameDESCAndPriceASC() throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_FIND_ALL_PERIODICAL_NAME_DESC_PRICE_ASC);
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_DESC_PRICE_ASC, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_DESC_PRICE_ASC, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals sorted by name ASC and price DESC.
     *
     * @return list of periodicals sorted by name ASC and price DESC.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalSortedByNameASCAndPriceDESC() throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        Statement stmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQLQueries.SQL_ALL_FIND_PERIODICAL_NAME_ASC_PRICE_DESC);
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_ASC_PRICE_DESC, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_ASC_PRICE_DESC, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals sorted by pattern.
     *
     * @return list of periodicals sorted by pattern.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalWithPattern(String pattern)
            throws DBException {
        List<Periodical> periodicals = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = utils.getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_ALL_PERIODICAL_WHERE_IS_PATTERN);
            pstmt.setString(1, "%" + pattern + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_PATTERN, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_PATTERN, ex);
        } finally {
            DBUtils.close(con);
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals sorted by pattern in specific List.
     *
     * @return list of periodicals sorted by pattern in specific List.
     * @throws DBException can be thrown.
     */
    public List<Periodical> findAllPeriodicalWithPatternInList(List<Periodical> periodicals, String pattern)
            throws DBException {
        List<Periodical> allPeriodicalsWithPattern = new DBManagerForPeriodicals()
                .findAllPeriodicalWithPattern(pattern);
        List<Periodical> specificPeriodicalsWithPattern = new ArrayList<>();
        for (Periodical periodical : periodicals) {
            for (Periodical periodicalWithPattern : allPeriodicalsWithPattern) {
                if (periodical.equals(periodicalWithPattern)) {
                    specificPeriodicalsWithPattern.add(periodicalWithPattern);
                }
            }
        }
        return specificPeriodicalsWithPattern;
    }
}
