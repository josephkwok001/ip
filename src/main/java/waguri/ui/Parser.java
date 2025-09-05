package waguri.ui;

/**
 * Parses user input commands for the Waguri chatbot application.
 * This class is responsible for interpreting raw user input and
 * converting it into recognized command types that the application can process.
 */
public class Parser {

    /**
     * Enumeration of all valid commands supported by the Waguri application.
     * Each command corresponds to a specific action the user can perform.
     */
    public enum Command {
        /** Creates a new todo task */ TODO,
        /** Creates a new deadline task */ DEADLINE,
        /** Creates a new event task */ EVENT,
        /** Deletes an existing task */ DELETE,
        /** Displays all tasks */ LIST,
        /** Marks a task as completed */ MARK,
        /** Marks a task as not completed */ UNMARK,
        /** Exits the application */ BYE,
        /** Shows tasks due by a specific date */ DUE,
        /** Represents an unrecognized or invalid command */ UNKNOWN,
        /** Searches for tasks containing specific text */ FIND
    }

    /**
     * Parses a raw user input string and determines the corresponding command type.
     * This method extracts the first word from the input and attempts to match it
     * against the available commands (case-insensitive).
     *
     * @param input the raw text input from the user
     * @return the corresponding Command enum value, or Command.UNKNOWN if the input
     *         is null, empty, or doesn't match any known command
     */
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

    /**
     * Generates a formatted string listing all available commands supported by the application.
     * This is useful for help messages or user guidance. The UNKNOWN command is excluded
     * from the list since it's not a valid user command.
     *
     * @return a comma-separated string of all available command names in lowercase,
     *         suitable for display to the user
     */
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
