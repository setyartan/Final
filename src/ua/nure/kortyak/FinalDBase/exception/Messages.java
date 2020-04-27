package ua.nure.kortyak.FinalDBase.exception;

/**
 * Holder for messages of exceptions.
 *
 * @author E.Kortyak
 */
public class Messages {



    private Messages() {
        // no op
    }

    public static final String ERR_CANNOT_OBTAIN_USER_BY_ID = "Cannot obtain a user by its id";
    public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Cannot obtain a user by its login";
    public static final String ERR_CANNOT_OBTAIN_ALL_USERS = "Cannot obtain all users by their ids";
    public static final String ERR_CANNOT_UPDATE_USER = "Cannot update a user";
    public static final String ERR_CANNOT_DELETE_USER = "Cannot delete a user";
    public static final String ERR_CANNOT_BLOCK_USER = "Cannot block a user";
    public static final String ERR_CANNOT_ALL_BLOCKED_USERS = "Cannot find all blocked users";
    public static final String ERR_CANNOT_UNBLOCK_USER = "Cannot unblock a users";
    //public static final String ERR_CANNOT_OBTAIN_USER_ORDER_BEANS = "Cannot obtain user order beans";

    public static final String ERR_CANNOT_OBTAIN_PERIODICAL_BY_ID = "Cannot obtain a periodical by its id";
    public static final String ERR_CANNOT_OBTAIN_PERIODICAL_BY_NAME = "Cannot obtain a periodical by its login";
    public static final String ERR_CANNOT_OBTAIN_ALL_PERIODICAL = "Cannot obtain all periodicals by their ids";
    public static final String ERR_CANNOT_UPDATE_PERIODICAL = "Cannot update a periodical";
    public static final String ERR_CANNOT_DELETE_PERIODICAL = "Cannot delete a periodical";
    public static final String ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_ASC = "Cannot obtain all periodicals by their names ASC";
    public static final String ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_PRICE_DESC = "Cannot obtain all periodicals by their names DESC";
    public static final String ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_PRICE_ASC = "Cannot obtain all periodicals by their prices ASC";
    public static final String ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_DESC = "Cannot obtain all periodicals by their prices DESC";
    public static final String ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_AND_PRICE_ASC = "Cannot obtain all periodicals by their names and prices ASC";
    public static final String ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_AND_PRICE_DESC = "Cannot obtain all periodicals by their names and prices DESC";
    public static final String ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_DESC_PRICE_ASC = "Cannot obtain all periodicals by their names DESC and prices ASC";
    public static final String ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_NAME_ASC_PRICE_DESC = "Cannot obtain all periodicals by their names ASC and prices DESC";
    public static final String ERR_CANNOT_OBTAIN_ALL_PERIODICAL_BY_PATTERN = "Cannot obtain all periodicals by pattern";

    public static final String ERR_CANNOT_FIND_ALL_PERIODICALS_OF_USER = "Cannot obtain all periodicals of a user";
    public static final String ERR_CANNOT_INSERT_PERIODICAL_FOR_USER = "Cannot insert all periodicals of a user";
    public static final String ERR_CANNOT_DELETE_PERIODICAL_FROM_USER = "Cannot delete all periodicals of a user";
    public static final String ERR_CANNOT_FIND_ALL_USERS_OF_PERIODICAL = "Cannot obtain all users of a periodical";

    public static final String ERR_CANNOT_OBTAIN_THEME_BY_ID = "Cannot obtain a theme by its id";
    public static final String ERR_CANNOT_OBTAIN_THEME_BY_LOGIN = "Cannot obtain a theme by its names";
    public static final String ERR_CANNOT_OBTAIN_ALL_THEMES = "Cannot obtain all themes by their ids";
    public static final String ERR_CANNOT_UPDATE_THEME = "Cannot update a theme";
    public static final String ERR_CANNOT_DELETE_THEME = "Cannot delete a theme";

    public static final String ERR_CANNOT_FIND_ALL_PERIODICALS_OF_THEME = "Cannot obtain all periodicals of a theme";
    public static final String ERR_CANNOT_INSERT_PERIODICAL_FOR_THEME = "Cannot insert all periodicals of a theme";
    public static final String ERR_CANNOT_DELETE_PERIODICAL_FROM_THEME = "Cannot delete all periodicals of a theme";
    public static final String ERR_CANNOT_FIND_THEME_OF_PERIODICAL = "Cannot find a theme of the periodical";

    public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection";
    public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection";
    public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close a result set";
    public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement";
    public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";
    public static final String ERR_CANNOT_ROLLBACK = "Cannot execute a rollback";
}
