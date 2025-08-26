package waguri.ui;

import java.util.Scanner;

public class Ui {
    private static final String BORDER = "═╦══════════════════════════════════════════════════════════╦═";
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showBorder();
        System.out.println("Hiiii, I'm your personal chat bot Waguri");
        System.out.println(" What can I do for you today?");
        showBorder();
    }

    public void showGoodbye() {
        showBorder();
        System.out.println("Bye. Hope to see you again soon!");
        showBorder();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String message) {
        showBorder();
        System.out.println("⚠️  " + message);
        showBorder();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showBorder() {
        System.out.println(BORDER);
    }

    public void showTaskList(String taskListString) {
        showBorder();
        System.out.println("YOUR LIST:");
        System.out.print(taskListString);
        showBorder();
    }

    public void close() {
        scanner.close();
    }
}