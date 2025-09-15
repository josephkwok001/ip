package waguri.ui;

import java.util.Scanner;

/**
 * Handles all user interface interactions for the Waguri chatbot application.
 * This class is responsible for displaying messages, reading user input,
 * and formatting output with consistent borders.
 */
public class Ui {
    private static final String BORDER = "═╦══════════════════════════════════════════════════════════╦═";

    private Scanner scanner;

    /**
     * Constructs a new Ui instance and initializes the scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the application starts.
     * Shows the border, introduction message, and closing border.
     */
    public void showWelcome() {
        showBorder();
        System.out.println("Hiiii, I'm your personal chat bot Waguri");
        System.out.println(" What can I do for you today?");
        showBorder();
    }

    /**
     * Displays the goodbye message when the application is exiting.
     * Shows the border, farewell message, and closing border.
     */
    public void showGoodbye() {
        showBorder();
        System.out.println("Bye. Hope to see you again soon!");
        showBorder();
    }

    public void showHelp() {
        System.out.println("Available commands: "
                + Parser.getAvailableCommands());
    }

    /**
     * Reads a command from the user via standard input.
     *
     * @return the user's input as a String, trimmed of leading/trailing whitespace
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message to the user with proper formatting.
     * Shows the border, error message with warning emoji, and closing border.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        showBorder();
        System.out.println("⚠️  " + message); // Warning emoji for visual emphasis
        showBorder();
    }

    /**
     * Displays the border line used for formatting UI sections.
     * This creates visual separation between different parts of the interface.
     */
    public void showBorder() {
        System.out.println(BORDER);
    }

    /**
     * Displays the user's task list with proper formatting.
     * Shows the border, list title, the actual task list content, and closing border.
     *
     * @param taskListString the formatted string containing all tasks to display
     */
    public void showTaskList(String taskListString) {
        showBorder();
        System.out.println("YOUR LIST:");
        System.out.print(taskListString);
        showBorder();
    }

}
