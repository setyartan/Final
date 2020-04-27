package ua.nure.kortyak.FinalDBase.web.command;

import org.apache.log4j.Logger;
import ua.nure.kortyak.FinalDBase.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * View settings command.
 *
 * @author E.Kortyak
 */
public class ViewSettingsCommand extends Command {

    private static final long serialVersionUID = -1296286577145974405L;

    private static final Logger LOG = Logger.getLogger(ViewSettingsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        LOG.debug("Command finished");
        return Path.PAGE_SETTINGS;
    }

}
