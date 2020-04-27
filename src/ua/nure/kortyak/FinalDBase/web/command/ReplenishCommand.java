package ua.nure.kortyak.FinalDBase.web.command;

import org.apache.log4j.Logger;
import ua.nure.kortyak.FinalDBase.Path;
import ua.nure.kortyak.FinalDBase.db.dbManager.DBManagerForUser;
import ua.nure.kortyak.FinalDBase.db.entity.User;
import ua.nure.kortyak.FinalDBase.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Replenish command.
 *
 * @author E.Kortyak
 */
public class ReplenishCommand extends Command {
    private static final long serialVersionUID = 3555869715159349851L;

    private static final Logger LOG = Logger.getLogger(ReplenishCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain value from a request
        DBManagerForUser managerForUser = DBManagerForUser.getInstance();
        String value = request.getParameter("value");
        LOG.trace("Request parameter: value --> " + value);

        String forward = Path.COMMAND_LIST_OWN_PERIODICALS;
        User user = (User) session.getAttribute("user");
        if (Long.parseLong(value) >= 0) {
            user.setAccount(user.getAccount() + Long.parseLong(value));
            managerForUser.updateUser(user);
            LOG.trace("Updated: user --> " + user);
        } else {
            forward = Path.PAGE_ERROR_PAGE;
            session.setAttribute("error", "7");
        }

        LOG.info("Updated: user --> " + user);

        LOG.debug("Command finished");
        return forward;
    }
}
