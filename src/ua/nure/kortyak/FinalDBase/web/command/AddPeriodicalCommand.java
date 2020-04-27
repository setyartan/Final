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
 * Add periodical command.
 *
 * @author E.Kortyak
 */
public class AddPeriodicalCommand extends Command {
    private static final long serialVersionUID = 5604424876549651224L;

    private static final Logger LOG = Logger.getLogger(AddPeriodicalCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain name, price and theme of periodical from a request
        String name = request.getParameter("name");
        LOG.trace("Request parameter: name --> " + name);

        String price = request.getParameter("price");
        LOG.trace("Request parameter: price --> " + price);

        String theme = request.getParameter("theme");
        LOG.trace("Request parameter: theme --> " + theme);

        if (name == null || price == null || name.isEmpty()
                || price.isEmpty() || theme == null || theme.isEmpty()) {
            session.setAttribute("error", "2");
            throw new AppException("Name/price/theme cannot be empty");
        }

        DBManagerForPeriodicals managerForPeriodicals = DBManagerForPeriodicals.getInstance();
        Periodical newPeriodical = Periodical.createPeriodical(name, Integer.parseInt(price));
        LOG.trace("Created: periodical --> " + newPeriodical);

        managerForPeriodicals.insertPeriodical(newPeriodical);
        LOG.trace("Inserted: periodical --> " + newPeriodical);

        DBManagerForThemes managerForThemes = DBManagerForThemes.getInstance();
        Theme themeOfPeriodical = managerForThemes.findThemeByName(theme);
        if (themeOfPeriodical == null) {
            managerForThemes.insertTheme(Theme.createTheme(theme));
            themeOfPeriodical = managerForThemes.findThemeByName(theme);
            LOG.trace("Inserted: theme --> " + theme);
        }

        DBManagerPOT managerPOT = DBManagerPOT.getInstance();
        managerPOT.insertPeriodicalsForTheme(themeOfPeriodical, newPeriodical);
        LOG.trace("Added theme to periodical: periodical --> "
                + newPeriodical + ", theme --> " + themeOfPeriodical);

        String forward = Path.COMMAND_LIST_ALL_PERIODICALS;

        List<Periodical> allPeriodicals = new DBManagerForPeriodicals().findAllPeriodical();
        session.setAttribute("periodicals", allPeriodicals);
        LOG.trace("Set the session attribute: periodicals --> " + allPeriodicals);

        LOG.info("Updated: periodical --> " + newPeriodical);

        LOG.debug("Command finished");
        return forward;
    }
}
