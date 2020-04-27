package ua.nure.kortyak.FinalDBase.web.command;

import org.apache.log4j.Logger;
import ua.nure.kortyak.FinalDBase.Path;
import ua.nure.kortyak.FinalDBase.db.dbManager.*;
import ua.nure.kortyak.FinalDBase.db.entity.Periodical;
import ua.nure.kortyak.FinalDBase.db.entity.Theme;
import ua.nure.kortyak.FinalDBase.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Edit periodical command.
 *
 * @author E.Kortyak
 */
public class EditPeriodicalCommand extends Command {
    private static final long serialVersionUID = 2639820297607981225L;

    private static final Logger LOG = Logger.getLogger(EditPeriodicalCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain oldName, oldPrice, oldTheme and newName, newPrice, newTheme  from a request
        DBManagerForPeriodicals managerForPeriodicals = DBManagerForPeriodicals.getInstance();
        String oldName = request.getParameter("oldName");
        LOG.trace("Request parameter: old name --> " + oldName);

        String newName = request.getParameter("newName");
        LOG.trace("Request parameter: old name --> " + newName);

        String newPrice = request.getParameter("newPrice");
        LOG.trace("Request parameter: old price --> " + newPrice);

        String newTheme = request.getParameter("newTheme");
        LOG.trace("Request parameter: old theme --> " + newTheme);

        String forward;
        Periodical newPeriodical = managerForPeriodicals.findPeriodicalByName(oldName);

        if (newPeriodical != null) {
            newPeriodical.setName(newName);
            newPeriodical.setPrice(Integer.parseInt(newPrice));
            LOG.trace("Update: periodical --> " + newPeriodical);

            managerForPeriodicals.updatePeriodical(newPeriodical);
            LOG.trace("Updated: periodical --> " + newPeriodical);

            DBManagerPOT managerPOT = DBManagerPOT.getInstance();
            DBManagerForThemes managerForThemes = DBManagerForThemes.getInstance();
            Theme oldTheme = managerForThemes
                    .findTheme(managerPOT.findThemeOfPeriodical(newPeriodical));
            LOG.trace("Found in DB: theme --> " + oldTheme);
            if (oldTheme != null && !(oldTheme.getName().equals(newTheme))) {
                Theme oldThemeOfPeriodical = managerForThemes.findThemeByName(oldTheme.getName());
                LOG.trace("Found in DB: theme --> " + oldThemeOfPeriodical);

                Theme newThemeOfPeriodical = managerForThemes.findThemeByName(newTheme);
                if (newThemeOfPeriodical != null) {
                    LOG.trace("Found in DB: theme --> " + newThemeOfPeriodical);
                } else {
                    managerForThemes.insertTheme(Theme.createTheme(newTheme));
                    newThemeOfPeriodical = managerForThemes.findThemeByName(newTheme);
                }
                managerPOT.deletePeriodicalOfTheme(newPeriodical, oldThemeOfPeriodical);
                LOG.trace("Deleted periodical of theme: periodical --> " + newPeriodical
                        + " theme --> " + oldThemeOfPeriodical);
                managerPOT.insertPeriodicalsForTheme(newThemeOfPeriodical, newPeriodical);
                LOG.trace("Inserted periodical for theme: periodical --> " + newPeriodical
                        + " theme --> " + newThemeOfPeriodical);

                if (managerPOT.findAllPeriodicalOfTheme(oldThemeOfPeriodical) == null
                        || managerPOT.findAllPeriodicalOfTheme(oldThemeOfPeriodical).isEmpty()) {
                    managerForThemes.deleteTheme(oldThemeOfPeriodical);
                    LOG.trace("Deleted: theme --> " + oldThemeOfPeriodical);
                }
            }
            forward = Path.COMMAND_LIST_ALL_PERIODICALS;
        } else {
            session.setAttribute("error", "6");
            throw new AppException("Cannot edit periodical");
        }
        List<Periodical> allPeriodicals = managerForPeriodicals.findAllPeriodical();
        session.setAttribute("periodicals", allPeriodicals);
        LOG.trace("Set the session attribute: periodicals --> " + allPeriodicals);

        LOG.info("Updated: periodical --> " + newPeriodical);

        LOG.debug("Command finished");
        return forward;
    }
}
