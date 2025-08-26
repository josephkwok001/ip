package waguri.ui;


public class Parser {
    public enum Command {
        TODO, DEADLINE, EVENT, DELETE, LIST, MARK, UNMARK, BYE, DUE, UNKNOWN
    }

    public static Command parseCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Command.UNKNOWN;
        }

        String[] parts = input.split(" ", 2);
        String commandWord = parts[0].toUpperCase();

        try {
            return Command.valueOf(commandWord);
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }
    }

    public static String getAvailableCommands() {
        StringBuilder commands = new StringBuilder();
        for (Command cmd : Command.values()) {
            if (cmd != Command.UNKNOWN) {
                if (commands.length() > 0) {
                    commands.append(", ");
                }
                commands.append(cmd.name().toLowerCase());
            }
        }
        return commands.toString();
    }
}