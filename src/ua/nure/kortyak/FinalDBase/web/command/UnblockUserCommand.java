package ua.nure.kortyak.FinalDBase.web.command;

import org.apache.log4j.Logger;
import ua.nure.kortyak.FinalDBase.Path;
import ua.nure.kortyak.FinalDBase.db.dbManager.DBManagerForUser;
import ua.nure.kortyak.FinalDBase.db.entity.User;
import ua.nure.kortyak.FinalDBase.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Unblock user command.
 *
 * @author E.Kortyak
 */
public class UnblockUserCommand extends Command {
    private static final long serialVersionUID = -2882158758468209796L;

    private static final Logger LOG = Logger.getLogger(UnblockUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain login from a request
        DBManagerForUser managerForUser = DBManagerForUser.getInstance();
        String login = request.getParameter("login");
        LOG.trace("Request parameter: login --> " + login);

        if (managerForUser.findUserByLogin(login) == null) {
            throw new AppException("User with this login does not exist to be unblocked");
        }

        User user = managerForUser.findUserByLogin(login);
        LOG.trace("Found in DB: user --> " + user);

        managerForUser.unblockUser(user);
        LOG.trace("Unblocked: user --> " + user);

        String forward = Path.COMMAND_LIST_ALL_PERIODICALS;

        List<User> allUsers = managerForUser.findAllUsers();
        session.setAttribute("users", allUsers);
        LOG.trace("Set the session attribute: all users --> " + allUsers);

        List<User> allBlockedUsers = managerForUser.findAllBlockedUsers();
        session.setAttribute("allBlockedUsers", allBlockedUsers);
        LOG.trace("Set the session attribute: unblocked users --> " + allBlockedUsers);

        List<User> NotBlockedUsers = new ArrayList<>();
        for (User userFromAll : allUsers)
            if (!allBlockedUsers.contains(userFromAll))
                NotBlockedUsers.add(userFromAll);
        session.setAttribute("notBlockedUsers", NotBlockedUsers);
        LOG.trace("Set the session attribute: not blocked users --> " + NotBlockedUsers);

        LOG.info("Unblocked: user --> " + user);

        LOG.debug("Command finished");
        return forward;
    }
}
