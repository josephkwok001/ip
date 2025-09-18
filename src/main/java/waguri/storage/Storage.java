package waguri.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import waguri.task.Deadline;
import waguri.task.Event;
import waguri.task.Task;
import waguri.task.TaskList;
import waguri.task.Todo;

/**
 * Handles file storage operations for tasks, including saving to and loading from a file.
 * Manages directory and file creation, task serialization, and deserialization.
 * Supports all task types (Todo, Deadline, Event) and maintains task status.
 */
public class Storage {
    /** The file path where tasks are stored */
    private String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     * Ensures the directory and file exist by creating them if necessary.
     *
     * @param filepath the path to the file where tasks will be stored
     */
    public Storage(String filepath) {
        this.filePath = filepath;
        doesDirectoryExists();
    }

    /**
     * Ensures that the directory containing the storage file exists.
     * Creates both the directory and the file if they don't already exist.
     * Handles IOException by printing error messages.
     */
    private void doesDirectoryExists() {
        try {
            File file = new File(filePath);
            File parentDirectory = file.getParentFile();

            if (!parentDirectory.exists()) {
                boolean directoryCreated = parentDirectory.mkdirs();
                if (directoryCreated) {
                    System.out.println("Created data directory: " + parentDirectory.getPath());
                }
            }
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    System.out.println("Created file: " + file.getPath());
                }
            }
            assert parentDirectory.exists()
                    : "Critical Failure: Parent directory does not exist after attempted creation: "
                            + parentDirectory.getAbsolutePath();
            assert file.exists()
                    : "Critical Failure: File does not exist after attempted creation: "
                            + file.getAbsolutePath();
            assert file.canWrite()
                    : "Critical Failure: Cannot write to file: "
                    + file.getAbsolutePath();

        } catch (IOException e) {
            System.out.println("Error creating directory/file: " + e.getMessage());
        }

    }

    /**
     * Saves all tasks to the storage file.
     * Each task is written as a string representation followed by a newline.
     *
     * @param tasks the ArrayList of tasks to be saved
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task t : tasks) {
                writer.write(t.toString() + "\n");
            }
            System.out.println("Tasks saved successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public String getStorageTask() {
        TaskList tasks = new TaskList(this.loadTasks());
        return tasks.getTasksAsString();
    }
    /**
     * Loads tasks from the storage file.
     * Parses each line of the file into the appropriate task type.
     *
     * @return an ArrayList of tasks loaded from the file
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Task task = parseTaskFromString(line);
                    tasks.add(task);
                }
            }
            System.out.println("Tasks loaded successfully from " + filePath);
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves a task to the archive file by appending it to existing archived tasks.
     * Loads all currently archived tasks from the file.
     *
     * @param t the Task object to be archived; must not be null
     */
    public void saveArchive(Task t) {
        ArrayList<Task> archiveList = this.loadTasks();
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : archiveList) {
                writer.write(task.toString() + "\n");
            }
            writer.write(t.toString() + "\n");
            System.out.println("Tasks saved to archive " + filePath);
        } catch (IOException e) {
            System.out.println("Error archiving tasks: " + e.getMessage());
        }
    }


    /**
     * Parses a single line of text into a Task object.
     * Handles all task types (Todo, Deadline, Event) and their status.
     *
     * @param line the string representation of a task from the storage file
     * @return the parsed Task object, or null if parsing fails
     */
    private Task parseTaskFromString(String line) {
        try {
            line = line.trim();
            String type = line.substring(1, 2);

            int descriptionStart = line.indexOf("] ", 3) + 2;
            boolean isDone = false;

            if (line.length() > 5 && line.charAt(3) == 'X') {
                isDone = true;
            }

            String remaining = line.substring(descriptionStart).trim();
            switch (type) {
            case "T":
                return createTodo(remaining, isDone);
            case "D":
                return createDeadline(remaining, isDone);
            case "E":
                return createEvent(remaining, isDone);
            default:
                break;
            }
        } catch (Exception e) {
            System.out.println("Error parsing task from: " + line + " - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Failed to parse: " + line);
        return null;
    }

    private Todo createTodo(String description, boolean isDone) {
        Todo todo = new Todo(description);
        if (isDone) {
            todo.markAsDone();
        }
        return todo;
    }

    private Deadline createDeadline(String remaining, boolean isDone) throws TaskParseException {
        if (!remaining.contains("(by:")) {
            throw new TaskParseException("Missing deadline time marker in: " + remaining);
        }

        int byIndex = remaining.indexOf("(by:");
        String description = remaining.substring(0, byIndex).trim();
        String time = remaining.substring(byIndex + 5, remaining.length() - 1).trim();

        LocalDateTime by = DateParser.parse(time);
        Deadline deadline = new Deadline(description, by);
        if (isDone) {
            deadline.markAsDone();
        }
        return deadline;
    }

    private Event createEvent(String remaining, boolean isDone) throws TaskParseException {
        if (!remaining.contains("(from:") || !remaining.contains("to:")) {
            throw new TaskParseException("Missing event time markers in: " + remaining);
        }

        int fromIndex = remaining.indexOf("(from:");
        String description = remaining.substring(0, fromIndex).trim();

        String time = remaining.substring(fromIndex + 6, remaining.length() - 1).trim();
        String[] timeParts = time.split(" to:");

        if (timeParts.length < 2) {
            throw new TaskParseException("Invalid event time format");
        }

        LocalDateTime from = DateParser.parse(timeParts[0].trim());
        LocalDateTime to = DateParser.parse(timeParts[1].trim());
        Event event = new Event(description, from, to);
        if (isDone) {
            event.markAsDone();
        }
        return event;

    }

    /**
     * Exception thrown when there is an error parsing task data.
     * This exception indicates that the task could not be interpreted properly
     */
    public class TaskParseException extends Exception {
        public TaskParseException(String message) {
            super(message);
        }
    }

}


