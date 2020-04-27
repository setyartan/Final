package ua.nure.kortyak.FinalDBase.web.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 *
 * @author E.Kortyak
 */
public class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("create", new CreateCommand());
        commands.put("logout", new LogoutCommander());
        commands.put("search", new SearchCommand());
        commands.put("viewSettings", new ViewSettingsCommand());
        commands.put("noCommand", new NoCommand());

        // commands of admin
        commands.put("addPeriodical", new AddPeriodicalCommand());
        commands.put("deletePeriodical", new DeletePeriodicalCommand());
        commands.put("editPeriodical", new EditPeriodicalCommand());
        commands.put("blockUser", new BlockUserCommand());
        commands.put("unblockUser", new UnblockUserCommand());

        // commands of logged users
        commands.put("replenish", new ReplenishCommand());
        commands.put("subscribe", new SubscribeCommand());

        LOG.debug("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }

}
