package ua.nure.kortyak.FinalDBase.web.command;

import org.apache.log4j.Logger;
import ua.nure.kortyak.FinalDBase.Path;
import ua.nure.kortyak.FinalDBase.db.dbManager.*;
import ua.nure.kortyak.FinalDBase.db.entity.Periodical;
import ua.nure.kortyak.FinalDBase.db.entity.Theme;
import ua.nure.kortyak.FinalDBase.db.entity.User;
import ua.nure.kortyak.FinalDBase.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Delete periodical command.
 *
 * @author E.Kortyak
 */
public class DeletePeriodicalCommand extends Command {
    private static final long serialVersionUID = -5714628891460872291L;

    private static final Logger LOG = Logger.getLogger(DeletePeriodicalCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain id from a request
        DBManagerForPeriodicals managerForPeriodicals = DBManagerForPeriodicals.getInstance();

        String id = request.getParameter("id");
        LOG.trace("Request parameter: id --> " + id);

        Periodical periodicalById = managerForPeriodicals.findPeriodical(Long.parseLong(id));

        String forward;

        if (periodicalById != null) {
            DBManagerForThemes managerForThemes = DBManagerForThemes.getInstance();

            DBManagerPOT managerPOT = DBManagerPOT.getInstance();

            Theme theme = managerForThemes.findTheme(managerPOT.findThemeOfPeriodical(periodicalById));
            managerPOT.deletePeriodicalOfTheme(periodicalById, theme);
            LOG.trace("Deleted: theme of periodical --> " + theme + " of " + periodicalById);

            managerForPeriodicals.deletePeriodical(periodicalById);
            LOG.trace("Deleted: periodical --> " + periodicalById);

            if (managerPOT.findAllPeriodicalOfTheme(theme).isEmpty()) {
                managerForThemes.deleteTheme(theme);
                LOG.trace("Deleted: theme --> " + theme);
            }

            DBManagerPOU managerPOU = DBManagerPOU.getInstance();
            List<User> usersOwingPeriodical = managerPOU.findAllUsersOfPeriodical(periodicalById);
            if (usersOwingPeriodical != null && !usersOwingPeriodical.isEmpty()) {
                for (User user : usersOwingPeriodical) {
                    managerPOU.deletePeriodicalOfUser(periodicalById, user);
                }
            }
            forward = Path.COMMAND_LIST_ALL_PERIODICALS;
        } else {
            session.setAttribute("error", "5");
            throw new AppException("Cannot find periodical with such name");
        }

        List<Periodical> allPeriodicals = managerForPeriodicals.findAllPeriodical();
        session.setAttribute("periodicals", allPeriodicals);
        LOG.trace("Set the session attribute: periodicals --> " + allPeriodicals);

        LOG.info("Deleted: periodical --> " + periodicalById);

        LOG.debug("Command finished");
        return forward;
    }
}
