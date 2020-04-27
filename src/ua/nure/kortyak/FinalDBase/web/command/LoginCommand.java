package ua.nure.kortyak.FinalDBase.web.command;

import org.apache.log4j.Logger;

import ua.nure.kortyak.FinalDBase.Path;
import ua.nure.kortyak.FinalDBase.db.dbManager.DBManagerForPeriodicals;
import ua.nure.kortyak.FinalDBase.db.dbManager.DBManagerForUser;
import ua.nure.kortyak.FinalDBase.db.dbManager.DBManagerPOU;
import ua.nure.kortyak.FinalDBase.db.entity.Periodical;
import ua.nure.kortyak.FinalDBase.db.entity.Role;
import ua.nure.kortyak.FinalDBase.db.entity.User;
import ua.nure.kortyak.FinalDBase.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Login command.
 *
 * @author E.Kortyak
 */
public class LoginCommand extends Command {

    private static final long serialVersionUID = -826871052187941056L;

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain login and password from a request
        DBManagerForUser managerForUser = DBManagerForUser.getInstance();
        String login = request.getParameter("login");
        LOG.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new AppException("Login/password cannot be empty");
        }
        User user = managerForUser.findUserByLogin(login);
        LOG.trace("Found in DB: user --> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            session.setAttribute("error", "1");
            throw new AppException("Cannot find user with such login/password");
        }

        Role userRole = Role.getRole(user);
        LOG.trace("userRole --> " + userRole);

        String forward = Path.PAGE_ERROR_PAGE;
        if (userRole == Role.ADMIN) {
            forward = Path.COMMAND_LIST_ALL_PERIODICALS;

            List<Periodical> allPeriodicals = DBManagerForPeriodicals
                    .getInstance().findAllPeriodical();
            session.setAttribute("periodicals",
                    new DBManagerForPeriodicals().findAllPeriodical());
            LOG.trace("Set the session attribute: all periodicals --> " + allPeriodicals);


            List<User> allBlockedUsers = managerForUser.findAllBlockedUsers();
            session.setAttribute("allBlockedUsers", allBlockedUsers);
            LOG.trace("Set the session attribute: blocked users --> " + allBlockedUsers);

            List<User> allUsers = managerForUser.findAllUsers();
            session.setAttribute("users", allUsers);
            LOG.trace("Set the session attribute: all users --> " + allUsers);

            List<User> NotBlockedUsers = new ArrayList<>();
            for (User userFromAll : allUsers) {
                if (!allBlockedUsers.contains(userFromAll)) {
                    NotBlockedUsers.add(userFromAll);
                }
            }
            session.setAttribute("notBlockedUsers", NotBlockedUsers);
            LOG.trace("Set the session attribute: not blocked users --> "
                    + NotBlockedUsers);
        }

        if (userRole == Role.CLIENT) {
            forward = Path.COMMAND_LIST_OWN_PERIODICALS;

            List<Periodical> ownPeriodicals = DBManagerPOU.getInstance().findAllPeriodicalOfUser(user);
            session.setAttribute("ownPeriodicals", ownPeriodicals);
            LOG.trace("Set the session attribute: own periodicals --> " + ownPeriodicals);
        }

        session.setAttribute("user", user);
        LOG.trace("Set the session attribute: user --> " + user);

        session.setAttribute("userRole", userRole);
        LOG.trace("Set the session attribute: userRole --> " + userRole);

        session.setAttribute("userLogin", user.getLogin());
        LOG.trace("Set the session attribute: userRole --> " + user.getLogin());

        List<Periodical> ownPeriodicals = DBManagerPOU.getInstance()
                .findAllPeriodicalOfUser(user);
        session.setAttribute("statuses", ownPeriodicals);
        LOG.trace("Set the session attribute: statuses --> " + ownPeriodicals);

        LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());


        LOG.debug("Command finished");
        return forward;
    }
}
