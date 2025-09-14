package waguri.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import waguri.WaguriException;
import waguri.storage.DateParser;

/**
 * Manages a collection of tasks and provides operations for task manipulation.
 * Handles creation, modification, deletion, and querying of various task types
 * including Todo, Deadline, and Event tasks. Also provides functionality to
 * find tasks due on specific dates and format task lists for display.
 */
public class TaskList {
    /** The list containing all tasks managed by this TaskList */
    private ArrayList<Task> tasks;

    public static final int MIN_TASK = 1;
    /**
     * Constructs a TaskList with the specified initial list of tasks.
     *
     * @param tasks the initial ArrayList of tasks to manage
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Marks a task as done based on its index in the list.
     * The index is 1-based (first task is index 1).
     *
     * @param index the 1-based index of the task to mark as done
     * @throws WaguriException if the index is out of bounds (less than 1 or greater than list size)
     */
    public void markTask(int index) throws WaguriException {
        if (index < MIN_TASK || index > tasks.size()) {
            throw new WaguriException("INVALID TASK NUMBER!");
        }
        tasks.get(index - 1).markAsDone();
    }

    /**
     * Marks a task as not done based on its index in the list.
     * The index is 1-based (first task is index 1).
     *
     * @param index the 1-based index of the task to unmark
     * @throws WaguriException if the index is out of bounds (less than 1 or greater than list size)
     */
    public void unmarkTask(int index) throws WaguriException {
        if (index < MIN_TASK || index > tasks.size()) {
            throw new WaguriException("INVALID TASK NUMBER!");
        }
        tasks.get(index - 1).unmark();
    }

    /**
     * Deletes a task based on its index in the list.
     * The index is 1-based (first task is index 1).
     *
     * @param index the 1-based index of the task to delete
     * @throws WaguriException if the index is out of bounds (less than 1 or greater than list size)
     */
    public void deleteTask(int index) throws WaguriException {
        if (index < MIN_TASK || index > tasks.size()) {
            throw new WaguriException("INVALID TASK NUMBER!");
        }
        tasks.remove(index - 1);
    }

    /**
     * Creates a new Todo task and adds it to the task list.
     *
     * @param description the description of the todo task
     * @throws WaguriException if the description is empty or contains only whitespace
     */
    public void createTodo(String description) throws WaguriException {
        if (description.trim().isEmpty()) {
            throw new WaguriException("Sir! A todo needs a description.");
        }
        tasks.add(new Todo(description));
    }

    /**
     * Creates a new Deadline task from user input and adds it to the task list.
     * Parses the input string to extract description and deadline time.
     *
     * @param input the user input containing task description and /by time
     * @throws WaguriException if the input format is invalid, missing /by, or has empty fields
     */
    public void createDeadline(String input) throws WaguriException {
        if (!input.contains("/by")) {
            throw new WaguriException("Sir! A deadline needs /by time. Usage: deadline [task] /by [time]");
        }

        String[] parts = input.split("/by", 2);
        String description = parts[0].trim();
        String byString = parts[1].trim();

        if (description.isEmpty() || byString.isEmpty()) {
            throw new WaguriException("Sir! Both description and time are required.");
        }

        LocalDateTime by = DateParser.parse(byString);
        tasks.add(new Deadline(description, by));
    }

    /**
     * Creates a new Event task from user input and adds it to the task list.
     * Parses the input string to extract description, start time, and end time.
     *
     * @param input the user input containing task description, /from start time, and /to end time
     * @throws WaguriException if the input format is invalid, missing /from or /to, or has empty fields
     */
    public void createEvent(String input) throws WaguriException {
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new WaguriException("An event needs /from and /to times.");
        }

        String[] parts = input.split("/from|/to");
        if (parts.length < 3) {
            throw new WaguriException("Usage: event [task] /from [start] /to [end]");
        }

        String description = parts[0].trim();
        String fromString = parts[1].trim();
        String toString = parts[2].trim();

        LocalDateTime from = DateParser.parse(fromString);
        LocalDateTime to = DateParser.parse(toString);
        tasks.add(new Event(description, from, to));
    }

    /**
     * Retrieves all tasks due on a specific date.
     * For Deadline tasks, checks if the deadline falls on the target date.
     * For Event tasks, checks if the event starts on the target date.
     *
     * @param dateString the date string to search for (will be parsed to LocalDateTime)
     * @return an ArrayList of tasks due on the specified date
     * @throws WaguriException if the date string cannot be parsed
     */
    public ArrayList<Task> getDueTasks(String dateString) throws WaguriException {
        LocalDateTime targetDate = DateParser.parse(dateString);
        return tasks.stream()
                .filter(task -> isTaskDueOnDate(task, targetDate))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isTaskDueOnDate(Task task, LocalDateTime targetDate) {
        if (task instanceof Deadline deadline) {
            return deadline.getBy().toLocalDate().equals(targetDate.toLocalDate());
        } else if (task instanceof Event event) {
            return event.getFrom().toLocalDate().equals(targetDate.toLocalDate());
        }
        return false;
    }

    public Task getTaskByIndex(int i){
        return tasks.get(i - 1);
    }


    /**
     * Formats the entire task list as a numbered string for display.
     *
     * @return a formatted string showing all tasks with numbers, or a message if the list is empty
     */
    public String getTasksAsString() {
        if (tasks.isEmpty()) {
            return "No tasks in your list!\n";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1) + ". " + tasks.get(i) + "\n");
        }
        return sb.toString();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Formats a list of due tasks with a descriptive header for display.
     *
     * @param dueTasks the list of tasks due on a specific date
     * @param date the date for which tasks are due
     * @return a formatted string showing tasks due on the specified date, or a message if none exist
     */
    public String formatDueTasks(ArrayList<Task> dueTasks, String date) {
        if (dueTasks.isEmpty()) {
            return "No tasks due on " + date + "!\n";
        }

        StringBuilder sb = new StringBuilder("Tasks due on " + date + ":\n");

        for (Task task : dueTasks) {
            sb.append(task + "\n");
        }

        return sb.toString();
    }

    /**
     * Finds a list of tasks that matches a description of the task.
     *
     * @param find String of the description of the task
     * @return an AraryList that contains a list of tasks that matches the string description
     */
    public ArrayList<Task> findTasks(String ... find) {
        return Arrays.stream(find)
                .flatMap(term -> tasks.stream()
                        .filter(task -> task.getDescription().contains(term))
                )
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean isEmpty() {
        if (this.tasks.get(0) == null) {
            return true;
        } else {
            return false;
        }
    }
}
