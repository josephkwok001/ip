package waguri.ui;


import java.util.ArrayList;

import waguri.storage.Storage;
import waguri.task.Task;
import waguri.task.TaskList;

public class GuiFormatter {
    private final TaskList tasks;
    private final Storage archiveStorage;

    /**
     * Constructs for GuiFormatter
     *
     * @param tasks the TaskList containing current tasks
     * @param archiveStorage the Storage object for archive operations
     */
    public GuiFormatter(TaskList tasks, Storage archiveStorage) {
        this.tasks = tasks;
        this.archiveStorage = archiveStorage;
    }

    public String generateResponse(Parser.Command command, String userExpression) {
        switch (command) {
        case BYE:
            return "🌟Goodbye!";

        case LIST:
            return formatTaskListResponse();

        case MARK:
            return "✅ VICTORY! Keep this momentum going! 💪";

        case UNMARK:
            return "🔄 You've got this!";

        case TODO:
            return "🎯 Goal set! You're making it happen! ✨";

        case DEADLINE:
            return "Deadline accepted! Pressure creates diamonds! 💎";

        case EVENT:
            return "Event Scheduled! ";

        case DELETE:
            return "Deleted!";

        case DUE:
            return formatDueTasksResponse(userExpression);

        case FIND:
            return formatFindResponse(userExpression);

        case ARCHIEVE:
            return formatArchiveResponse();

        case UNKNOWN:
            return "ERROR: I do not understand that command. "
                    + "Try: list, todo, deadline, event, mark, unmark, delete";

        case HELP:
            return "Available commands are: "
                    + Parser.getAvailableCommands();
        default:
            return "Keep building your future! 🏆";
        }
    }

    private String formatTaskListResponse() {
        if (tasks.isEmpty()) {
            return "Your list is empty! What will you achieve today?";
        }
        return tasks.getTasksAsString().replaceAll("\\[X\\]", "✅");
    }

    private String formatDueTasksResponse(String userInput) {
        try {
            String date = userInput.substring(3).trim();
            ArrayList<Task> dueTasks = tasks.getDueTasks(date);
            return "Your upcoming tasks: " + tasks.formatDueTasks(dueTasks, date);
        } catch (waguri.WaguriException e) {
            return "Error: " + e.getMessage();
        }
    }

    private String formatFindResponse(String userInput) {
        String searchQuery = userInput.substring(5).trim();
        String[] searchTerms = searchQuery.split("\\s+");
        TaskList foundTasks = new TaskList(tasks.findTasks(searchTerms));
        return foundTasks.getTasksAsString();
    }

    private String formatArchiveResponse() {
        String archiveTasks = archiveStorage.getStorageTask();
        if (archiveTasks.isEmpty()) {
            return "Your archive is empty!";
        }
        String formattedTasks = archiveTasks.replaceAll("\\[X\\]", "✅");
        return "Your hall of achievements:\n" + formattedTasks;
    }

}
