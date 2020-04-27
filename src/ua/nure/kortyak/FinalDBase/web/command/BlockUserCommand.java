package ua.nure.kortyak.FinalDBase.web.command;

import org.apache.log4j.Logger;
import ua.nure.kortyak.FinalDBase.Path;
import ua.nure.kortyak.FinalDBase.db.dbManager.DBManagerForUser;
import ua.nure.kortyak.FinalDBase.db.entity.Role;
import ua.nure.kortyak.FinalDBase.db.entity.User;
import ua.nure.kortyak.FinalDBase.exception.AppException;
import ua.nure.kortyak.FinalDBase.exception.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Block user command.
 *
 * @author E.Kortyak
 */
public class BlockUserCommand extends Command {

    private static final long serialVersionUID = 5254655976426461262L;

    private static final Logger LOG = Logger.getLogger(BlockUserCommand.class);

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
            session.setAttribute("error", "3");
            throw new AppException("User with this login does not exist to be blocked");
        }
        User user = managerForUser.findUserByLogin(login);
        LOG.trace("Found in DB: user --> " + user);

        String forward;
        if (Role.getRole(user) != Role.ADMIN) {
            block(user);
            forward = Path.COMMAND_LIST_ALL_PERIODICALS;
        } else {
            forward = Path.PAGE_ERROR_PAGE;
        }
        settingAttributes(session);

        LOG.info("Blocked: user --> " + user);

        LOG.debug("Command finished");
        return forward;
    }

    private void block(User user) throws DBException {
        DBManagerForUser managerForUser = DBManagerForUser.getInstance();
        managerForUser.blockUser(user);
        LOG.trace("Blocked: user --> " + user);
    }

    private void settingAttributes(HttpSession session) throws DBException {
        DBManagerForUser managerForUser = DBManagerForUser.getInstance();

        List<User> allBlockedUsers = managerForUser.findAllBlockedUsers();
        session.setAttribute("allBlockedUsers", allBlockedUsers);
        LOG.trace("Set the session attribute: blocked users --> " + allBlockedUsers);

        List<User> allUsers = managerForUser.findAllUsers();
        List<User> allNotBlockedUsers = new ArrayList<>();
        for (User allUser : allUsers) {
            if (!allBlockedUsers.contains(allUser)) {
                allNotBlockedUsers.add(allUser);
            }
        }
        session.setAttribute("notBlockedUsers", allNotBlockedUsers);
        LOG.trace("Set the session attribute: not blocked users --> " + allNotBlockedUsers);
    }

}
