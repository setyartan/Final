package ua.nure.kortyak.FinalDBase.web.command;

import org.apache.log4j.Logger;

import ua.nure.kortyak.FinalDBase.Path;
import ua.nure.kortyak.FinalDBase.db.dbManager.DBManagerForUser;
import ua.nure.kortyak.FinalDBase.db.entity.Role;
import ua.nure.kortyak.FinalDBase.db.entity.User;
import ua.nure.kortyak.FinalDBase.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Create account command.
 *
 * @author E.Kortyak
 */
public class CreateCommand extends Command {

    private static final Logger LOG = Logger.getLogger(CreateCommand.class);

    private static final long serialVersionUID = 9093356950535797361L;

    private static final int defaultAccount = 0;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain login, password, first nae and second name from a request
        DBManagerForUser managerForUser = DBManagerForUser.getInstance();
        String login = request.getParameter("login");
        LOG.trace("Request parameter: login --> " + login);

        if (managerForUser.findUserByLogin(login) != null) {
            session.setAttribute("error", "4");
            throw new AppException("User with this login already exists");
        }

        String password = request.getParameter("password");

        String firstName = request.getParameter("first_name");
        LOG.trace("Request parameter: firstName --> " + firstName);

        String lastName = request.getParameter("last_name");
        LOG.trace("Request parameter: lastName --> " + lastName);

        User user = User.createUser
                (login, password, firstName, lastName, Role.CLIENT.ordinal(), defaultAccount);
        LOG.trace("Created user: user --> " + user);

        managerForUser.insertUser(user);
        LOG.trace("Inserted in DB: user --> " + user);

        String forward = Path.COMMAND_LIST_OWN_PERIODICALS;
        setAttribute(session, user);

        LOG.info("User " + user + " created an account and logged as "
                + Role.CLIENT.toString());

        LOG.debug("Command finished");
        return forward;
    }

    private void setAttribute(HttpSession session, User user){
        session.setAttribute("user", user);
        LOG.trace("Set the session attribute: user --> " + user);

        session.setAttribute("userRole", user.getRoleId());
        LOG.trace("Set the session attribute: userRole --> " + user.getRoleId());

    }
}
