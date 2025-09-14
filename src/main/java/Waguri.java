package waguri;

import java.util.ArrayList;

import waguri.storage.Storage;
import waguri.task.Task;
import waguri.task.TaskList;
import waguri.ui.Parser;
import waguri.ui.Ui;


/**
 * Main class for the Waguri task management application.
 * Coordinates between user interface, task management, and storage components.
 * Handles the main application lifecycle including initialization, command processing,
 * and shutdown.
 */
public class Waguri {
    /** The storage component responsible for saving and loading tasks */
    private Storage storage;
    /** The task list component managing all tasks */
    private TaskList tasks;
    /** The user interface component handling input and output */
    private Ui ui;
    /** The storage component responsible for archive */
    private Storage archiveStorage;


    /**
     * Constructs a new Waguri application instance.
     * Initializes the UI, storage, and task list components.
     *
     * @param filePath the file path where tasks will be persisted
     */
    public Waguri() {
        this.ui = new Ui();
        this.storage = new Storage("./data/waguri.txt");
        this.tasks = new TaskList(storage.loadTasks());
        this.archiveStorage = new Storage("./data/waguriArchive.txt");
    }

    public Waguri(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(storage.loadTasks());
        this.archiveStorage = new Storage("./data/waguriArchive.txt");
    }

    /**
     * Starts the main application loop.
     * Displays welcome message and processes user commands until exit.
     * Handles exceptions and displays appropriate error messages.
     */
    public void run() {
        ui.showWelcome();

        boolean isRunning = true;
        while (isRunning) {
            try {
                String userExpression = ui.readCommand();
                Parser.Command command = Parser.parseCommand(userExpression);
                isRunning = processCommand(command, userExpression);
            } catch (waguri.WaguriException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Unexpected Error: " + e.getMessage());
            }
        }
    }

    /**
     * Processes a single user command and executes the corresponding action.
     * Handles all supported command types and manages task persistence.
     *
     * @param command the parsed command type
     * @param userExpression the original user input string
     * @return false if the application should exit (BYE command), true otherwise
     * @throws WaguriException if the command is invalid or processing fails
     */
    private boolean processCommand(Parser.Command command, String userExpression) throws waguri.WaguriException {
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
            archiveStorage.saveArchive(tasks.getTaskByIndex(index));
            tasks.deleteTask(index);
            storage.saveTasks(tasks.getTasks());
            return true;
        case DUE:
            String date = userExpression.substring(3).trim();
            ArrayList<Task> dueTasks = tasks.getDueTasks(date);
            ui.showTaskList(tasks.formatDueTasks(dueTasks, date));
            return true;
        case FIND:
            String findContent = userExpression.substring(5).trim();
            String[] searchTerms = findContent.split("\\s+");
            TaskList findTasks = new TaskList(tasks.findTasks(searchTerms));
            ui.showTaskList(findTasks.getTasksAsString());
            return true;
        case ARCHIEVE:
            TaskList archieveTask = new TaskList(archiveStorage.loadTasks());
            ui.showTaskList(archieveTask.getTasksAsString());
            return true;
        case UNKNOWN:
            throw new waguri.WaguriException("COMMAND NOT RECOGNIZED! Available commands: "
                    + Parser.getAvailableCommands());
        default:
            throw new waguri.WaguriException("Unexpected command: " + command);
        }
    }

    public static void main(String[] args) {
        new Waguri("./data/waguri.txt").run();
    }

    public String getResponse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Hello? Is anyone there?";
        }

        try {
            String userExpression = input.trim();
            Parser.Command command = Parser.parseCommand(userExpression);

            // Process the command using your existing method
            processCommand(command, userExpression);

            // Return simple success messages
            switch (command) {
            case BYE:
                return "Goodbye! Hope to see you again soon!";
            case LIST:
                return "Here are your tasks:\n" + tasks.getTasksAsString();
            case MARK:
                return "Nice! I've marked that task as done! ✅";
            case UNMARK:
                return "OK, I've marked that task as not done yet.";
            case TODO:
            case DEADLINE:
            case EVENT:
                return "Got it! I've added that task. 📝";
            case DELETE:
                return "Noted. I've removed that task! 🗑️";
            case DUE:
                return "Here are your tasks due on that date:";
            case FIND:
                return "Here are the matching tasks:";
            case ARCHIEVE:
                return "Here are your archived tasks:";
            case UNKNOWN:
                return "ERROR: I don't understand that command. Try: list, todo, deadline, event, mark, unmark, delete";
            default:
                return "Command processed!";
            }

        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
