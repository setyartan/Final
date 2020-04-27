package ua.nure.kortyak.FinalDBase.web.command;

import org.apache.log4j.Logger;
import ua.nure.kortyak.FinalDBase.Path;
import ua.nure.kortyak.FinalDBase.db.dbManager.DBManagerForPeriodicals;
import ua.nure.kortyak.FinalDBase.db.dbManager.DBManagerForThemes;
import ua.nure.kortyak.FinalDBase.db.dbManager.DBManagerPOT;
import ua.nure.kortyak.FinalDBase.db.entity.Periodical;
import ua.nure.kortyak.FinalDBase.db.finalClasses.Fields;
import ua.nure.kortyak.FinalDBase.exception.AppException;
import ua.nure.kortyak.FinalDBase.exception.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Search command.
 *
 * @author E.Kortyak
 */
public class SearchCommand extends Command {

    private static final long serialVersionUID = 543356642993398854L;

    private static final Logger LOG = Logger.getLogger(SearchCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain search pattern and sorting parameters from a request
        DBManagerForPeriodicals managerForPeriodicals = DBManagerForPeriodicals.getInstance();
        String search = request.getParameter("search");
        LOG.trace("Request parameter: search --> " + search);

        String sortingByName = request.getParameter("sortingByName");
        LOG.trace("Request parameter: sortingByName --> " + sortingByName);

        String sortingByPrice = request.getParameter("sortingByPrice");
        LOG.trace("Request parameter: sortingByPrice --> " + sortingByPrice);

        DBManagerForThemes managerForThemes = DBManagerForThemes.getInstance();
        String theme = request.getParameter("theme");
        LOG.trace("Request parameter: theme --> " + theme);
        List<Periodical> periodicals = null;
        if ((theme == null && search == null) || (theme.isEmpty() && search.isEmpty())) {
            periodicals = sortingFirstCondition(sortingByName, sortingByPrice);
        } else {
            if (managerForThemes.findThemeByName(theme) != null) {
                periodicals = sortingFirstCondition(sortingByName, sortingByPrice);
                periodicals = DBManagerPOT.getInstance()
                        .findAllPeriodicalOfThemeWithSorting(periodicals,
                                DBManagerForThemes.getInstance().findThemeByName(theme));
            }
            if (search != null) {
                if (!search.isEmpty()) {
                    periodicals = sortingFirstCondition(sortingByName, sortingByPrice);
                    periodicals = managerForPeriodicals
                            .findAllPeriodicalWithPatternInList(periodicals, search);
                }
            }

            if ((periodicals == null || periodicals.isEmpty())) {
                periodicals = new ArrayList<>();
                periodicals.add(0, Periodical.createPeriodical
                        (Fields.NO_RESULTS, -1));
            }
        }

        LOG.trace("Search result: periodicals --> " + periodicals);

        String forward = Path.SEARCH_PAGE;

        session.setAttribute("list", periodicals);
        LOG.trace("Set the session attribute: periodicals --> " + periodicals);

        LOG.debug("Command finished");
        return forward;

    }

    /**
     * Returns a list of periodicals with the given sorting.
     *
     * @param sortingByName  sorting condition.
     * @param sortingByPrice sorting condition.
     * @return List of sorted periodicals.
     */
    private List<Periodical> sortingFirstCondition
    (String sortingByName, String sortingByPrice) throws DBException {
        List<Periodical> periodicals;
        if (Fields.SORTING_NONE.equals(sortingByName)) {
            periodicals = sortingFirstNoneSecondCondition(sortingByPrice);
        } else if (Fields.SORTING_UP.equals(sortingByName)) {
            periodicals = sortingFirstUpSecondCondition(sortingByPrice);
        } else if (Fields.SORTING_DOWN.equals(sortingByName)) {
            periodicals = sortingFirstDownSecondCondition(sortingByPrice);
        } else {
            periodicals = new DBManagerForPeriodicals().findAllPeriodical();
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals with the given sorting.
     *
     * @param sortingByPrice sorting condition.
     * @return List of sorted periodicals.
     */
    private List<Periodical> sortingFirstNoneSecondCondition(String sortingByPrice)
            throws DBException {
        List<Periodical> periodicals = null;
        if (Fields.SORTING_NONE.equals(sortingByPrice)) {
            periodicals = new DBManagerForPeriodicals().findAllPeriodical();
        } else if (Fields.SORTING_UP.equals(sortingByPrice)) {
            periodicals = new DBManagerForPeriodicals().findAllPeriodicalSortedByPriceASC();
        } else if (Fields.SORTING_DOWN.equals(sortingByPrice)) {
            periodicals = new DBManagerForPeriodicals().findAllPeriodicalSortedByPriceDESC();
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals with the given sorting.
     *
     * @param sortingByPrice sorting condition.
     * @return List of sorted periodicals.
     */
    private List<Periodical> sortingFirstUpSecondCondition(String sortingByPrice)
            throws DBException {
        List<Periodical> periodicals = null;
        if (Fields.SORTING_NONE.equals(sortingByPrice)) {
            periodicals = new DBManagerForPeriodicals().findAllPeriodicalSortedByNameASC();
        } else if (Fields.SORTING_UP.equals(sortingByPrice)) {
            periodicals = new DBManagerForPeriodicals().findAllPeriodicalSortedByNameAndPriceASC();
        } else if (Fields.SORTING_DOWN.equals(sortingByPrice)) {
            periodicals = new DBManagerForPeriodicals().findAllPeriodicalSortedByNameASCAndPriceDESC();
        }
        return periodicals;
    }

    /**
     * Returns a list of periodicals with the given sorting.
     *
     * @param sortingByPrice sorting condition.
     * @return List of sorted periodicals.
     */
    private List<Periodical> sortingFirstDownSecondCondition(String sortingByPrice)
            throws DBException {
        List<Periodical> periodicals = null;
        if (Fields.SORTING_NONE.equals(sortingByPrice)) {
            periodicals = new DBManagerForPeriodicals().findAllPeriodicalSortedByNameDESC();
        } else if (Fields.SORTING_UP.equals(sortingByPrice)) {
            periodicals = new DBManagerForPeriodicals().findAllPeriodicalSortedByNameDESCAndPriceASC();
        } else if (Fields.SORTING_DOWN.equals(sortingByPrice)) {
            periodicals = new DBManagerForPeriodicals().findAllPeriodicalSortedByNameAndPriceDESC();
        }
        return periodicals;
    }
}
