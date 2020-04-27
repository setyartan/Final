package ua.nure.kortyak.FinalDBase.db.finalClasses;

/**
 * Holder for SQL queries of DB tables and beans.
 *
 * @author E.Kortyak
 */
public class SQLQueries {

    //SQL for users
    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";
    public static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

    public static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_DELETE_USER = "DELETE FROM users WHERE id=?";
    public static final String SQL_UPDATE_USER = "UPDATE users SET password=?, first_name=?, last_name=?, account=? WHERE id=?";
    public static final String SQL_BLOCK_USER = "INSERT INTO blocked_users VALUES (?)";
    public static final String SQL_FIND_ALL_BLOCKED_USERS = "SELECT user_id FROM blocked_users";
    public static final String SQL_UNBLOCK_USER = "DELETE FROM blocked_users WHERE user_id=?";

    //SQL for periodicals
    public static final String SQL_FIND_ALL_PERIODICALS = "SELECT * FROM periodicals";
    public static final String SQL_FIND_PERIODICAL_BY_ID = "SELECT * FROM periodicals WHERE id=?";
    public static final String SQL_FIND_PERIODICAL_BY_NAME = "SELECT * FROM periodicals WHERE name=?";

    public static final String SQL_INSERT_PERIODICAL = "INSERT INTO periodicals VALUES (DEFAULT, ?, ?)";
    public static final String SQL_DELETE_PERIODICAL = "DELETE FROM periodicals WHERE id=?";
    public static final String SQL_UPDATE_PERIODICAL = "UPDATE periodicals SET name=?, price=? WHERE id=?";

    //SQL sort periodicals
    public static final String SQL_FIND_ALL_PERIODICAL_NAME_ORDER_BY_ASC = "SELECT * FROM periodicals ORDER BY name ASC";
    public static final String SQL_FIND_ALL_PERIODICAL_NAME_ORDER_BY_DESC = "SELECT * FROM periodicals ORDER BY name DESC";
    public static final String SQL_FIND_ALL_PERIODICAL_PRICE_ORDER_BY_ASC = "SELECT * FROM periodicals ORDER BY price ASC";
    public static final String SQL_FIND_ALL_PERIODICAL_PRICE_ORDER_BY_DESC = "SELECT * FROM periodicals ORDER BY price DESC";
    public static final String SQL_FIND_ALL_PERIODICAL_NAME_AND_PRICE_ORDER_BY_ASC = "SELECT * FROM periodicals ORDER BY name ASC, price ASC";
    public static final String SQL_FIND_ALL_PERIODICAL_NAME_AND_PRICE_ORDER_BY_DESC = "SELECT * FROM periodicals ORDER BY name DESC, price DESC";
    public static final String SQL_ALL_FIND_PERIODICAL_NAME_ASC_PRICE_DESC = "SELECT * FROM periodicals ORDER BY name ASC, price DESC";
    public static final String SQL_FIND_ALL_PERIODICAL_NAME_DESC_PRICE_ASC = "SELECT * FROM periodicals ORDER BY name DESC, price ASC";
    public static final String SQL_FIND_ALL_PERIODICAL_WHERE_IS_PATTERN = "SELECT * FROM periodicals WHERE name LIKE ?";

    //SQL for periodicals of user
    public static final String SQL_FIND_ALL_PERIODICALS_OF_USER = "SELECT * FROM periodicals_of_user WHERE user_id=?";
    public static final String SQL_INSERT_PERIODICAL_FOR_USER = "INSERT INTO periodicals_of_user VALUES (?, ?)";
    public static final String SQL_DELETE_PERIODICAL_FROM_USER = "DELETE FROM periodicals_of_user WHERE periodical_id=? AND user_id=?";
    public static final String SQL_FIND_ALL_USERS_OF_PERIODICAL = "SELECT * FROM periodicals_of_user WHERE periodical_id=?";

    //SQL for themes
    public static final String SQL_FIND_ALL_THEMES = "SELECT * FROM themes";
    public static final String SQL_FIND_THEME_BY_ID = "SELECT * FROM themes WHERE id=?";
    public static final String SQL_FIND_THEME_BY_NAME = "SELECT * FROM themes WHERE name=?";

    public static final String SQL_INSERT_THEME = "INSERT INTO themes VALUES (DEFAULT, ?)";
    public static final String SQL_DELETE_THEME = "DELETE FROM themes WHERE id=?";
    public static final String SQL_UPDATE_THEME = "UPDATE themes SET name=? WHERE id=?";

    //SQL for themes of periodical
    public static final String SQL_FIND_ALL_PERIODICALS_OF_THEME = "SELECT * FROM periodicals_of_theme WHERE theme_id=?";
    public static final String SQL_INSERT_PERIODICAL_FOR_THEME = "INSERT INTO periodicals_of_theme VALUES (?, ?)";
    public static final String SQL_DELETE_PERIODICAL_FROM_THEME = "DELETE FROM periodicals_of_theme WHERE periodical_id=? AND theme_id=?";
    public static final String SQL_FIND_THEME_OF_PERIODICAL = "SELECT theme_id FROM periodicals_of_theme WHERE periodical_id=?";

}
