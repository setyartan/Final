package ua.nure.kortyak.FinalDBase.web.command;

import org.apache.log4j.Logger;
import ua.nure.kortyak.FinalDBase.Path;
import ua.nure.kortyak.FinalDBase.db.dbManager.*;
import ua.nure.kortyak.FinalDBase.db.entity.Periodical;
import ua.nure.kortyak.FinalDBase.db.entity.User;
import ua.nure.kortyak.FinalDBase.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Subscribe command.
 *
 * @author E.Kortyak
 */
public class SubscribeCommand extends Command {

    private static final long serialVersionUID = -4791218127494610072L;

    private static final Logger LOG = Logger.getLogger(SubscribeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain name and login from a request
        DBManagerForUser managerForUser = DBManagerForUser.getInstance();
        DBManagerForPeriodicals managerForPeriodicals = DBManagerForPeriodicals.getInstance();

        String name = request.getParameter("periodical");
        Periodical periodical = managerForPeriodicals.findPeriodicalByName(name);
        LOG.trace("Found in DB: periodical --> " + periodical);

        String login = request.getParameter("user");
        User user = managerForUser.findUserByLogin(login);
        LOG.trace("Found in DB: user --> " + user);

        String forward;
        DBManagerPOU managerPOU = DBManagerPOU.getInstance();

        if (periodical != null && user.getAccount() - periodical.getPrice() >= 0) {
            user.setAccount(user.getAccount() - periodical.getPrice());
            managerForUser.updateUser(user);
            LOG.trace("Updated money after the subscribing: user --> " + user);

            managerPOU.insertPeriodicalsForUser(user, periodical);
            LOG.trace("Inserted periodical to user:" + " periodical --> " + periodical
                    + " user --> " + user);

            forward = Path.SEARCH_PAGE;
        } else {
            forward = Path.PAGE_ERROR_PAGE;
			  session.setAttribute("error", "8");
        }

        List<Periodical> allPeriodicals = managerForPeriodicals.findAllPeriodical();
        session.setAttribute("periodicals", allPeriodicals);
        LOG.trace("Set the session attribute: periodicals --> " + allPeriodicals);

        List<Periodical> periodicalsOfUser = managerPOU.findAllPeriodicalOfUser(user);
        session.setAttribute("ownPeriodicals", periodicalsOfUser);
        LOG.info("Set the session attribute: periodicals of user --> " + user
                + " periodical --> " + periodicalsOfUser);

        session.setAttribute("user", user);
        LOG.trace("Set the session attribute: user --> " + user);

        List<Periodical> ownPeriodicals = DBManagerPOU.getInstance().findAllPeriodicalOfUser(user);
        session.setAttribute("statuses", ownPeriodicals);
        LOG.trace("Set the session attribute: statuses --> " + ownPeriodicals);

        LOG.info("Added periodical to user: user --> " + user + " periodical --> " + periodical);

        LOG.debug("Command finished");
        return forward;
    }
}
