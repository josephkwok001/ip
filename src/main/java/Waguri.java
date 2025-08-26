import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.MonthDay;

public class Waguri {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Waguri(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(storage.loadTasks());
    }

    public void run() {
        ui.showWelcome();

        boolean isRunning = true;
        while (isRunning) {
            try {
                String userExpression = ui.readCommand();
                Parser.Command command = Parser.parseCommand(userExpression);
                isRunning = processCommand(command, userExpression);
            } catch (WaguriException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Unexpected Error: " + e.getMessage());
            }
        }
    }

    private boolean processCommand(Parser.Command command, String userExpression) throws WaguriException {
        switch (command) {
            case BYE:
                ui.showGoodbye();
                return false;

            case LIST:
                ui.showTaskList(tasks.getTasksAsString());
                return true;

            case MARK:
                int taskNumber = Integer.parseInt(userExpression.substring(5));
                tasks.markTask(taskNumber);
                storage.saveTasks(tasks.getTasks());
                return true;

            case UNMARK:
                int unmarkTaskNumber = Integer.parseInt(userExpression.substring(7));
                tasks.unmarkTask(unmarkTaskNumber);
                storage.saveTasks(tasks.getTasks());
                return true;

            case TODO:
                tasks.createTodo(userExpression.substring(5));
                storage.saveTasks(tasks.getTasks());
                return true;

            case DEADLINE:
                tasks.createDeadline(userExpression.substring(9).trim());
                storage.saveTasks(tasks.getTasks());
                return true;

            case EVENT:
                tasks.createEvent(userExpression.substring(6).trim());
                storage.saveTasks(tasks.getTasks());
                return true;

            case DELETE:
                int index = Integer.parseInt(userExpression.substring(7));
                tasks.deleteTask(index);
                storage.saveTasks(tasks.getTasks());
                return true;

            case DUE:
                String date = userExpression.substring(3).trim();
                ArrayList<Task> dueTasks = tasks.getDueTasks(date);
                ui.showTaskList(formatDueTasks(dueTasks, date));
                return true;

            case UNKNOWN:
                throw new WaguriException("COMMAND NOT RECOGNIZED! Available commands: " +
                        Parser.getAvailableCommands());

            default:
                throw new WaguriException("Unexpected command: " + command);
        }
    }

    private String formatDueTasks(ArrayList<Task> dueTasks, String date) {
        if (dueTasks.isEmpty()) {
            return "No tasks due on " + date + "!\n";
        }

        StringBuilder sb = new StringBuilder("Tasks due on " + date + ":\n");
        for (Task task : dueTasks) {
            sb.append(task + "\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        new Waguri("./data/waguri.txt").run();
    }
}

class WaguriException extends Exception {
    public WaguriException(String message) {
        super(message);
    }
}