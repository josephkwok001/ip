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
        case HELP:
            ui.showHelp();
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

            processCommand(command, userExpression);

            switch (command) {
            case BYE:
                return "🌟 Remember: Every ending is a new beginning. I'll be here when you're ready to conquer your next goals!";

            case LIST:
                if (tasks.isEmpty()) {
                    return "🎯 Your canvas is empty! This is your opportunity to create something amazing. What will you achieve today?";
                } else {
                    String taskList = tasks.getTasksAsString();
                    String formattedTasks = taskList.replaceAll("\\[X\\]", "✅");

                    return formattedTasks;
                }

            case MARK:
                return "✅ VICTORY! This is how champions are made! Keep this momentum going! 💪";

            case UNMARK:
                return "🔄 You've got this!";

            case TODO:
                return "🎯 Goal set! You're making it happen! ✨";

            case DEADLINE:
                return "⏰ Deadline accepted! Pressure creates diamonds! 💎";

            case EVENT:
                return "📅 Scheduled! ";

            case DELETE:
                return "🗑️ Deleted!";

            case DUE:
                return "📅 Your upcoming milestones:\n" +
                        "Remember: The best time to plant a tree was 20 years ago. The second best time is now! 🌳";

            case FIND:
                return "🔍 Found your priorities! ⚡";

            case ARCHIEVE:
                String archiveTasks = archiveStorage.getStorageTask();
                if (archiveTasks.isEmpty()) {
                    return "📦 Your archive awaits future accomplishments!";
                } else {
                    return "📦 Your hall of achievements:\n" + archiveTasks +
                            "\n\nLook how far you've come! Your past efforts have built who you are today! 🌟";
                }

            case UNKNOWN:
                return "ERROR: I don't understand that command. Try: list, todo, deadline, event, mark, unmark, delete";

            case HELP:
                return "Available commands are: " +
                        Parser.getAvailableCommands();
            default:
                return "⚡ Progress made! Small steps every day lead to massive results. Keep building your future! 🏆";
            }
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
