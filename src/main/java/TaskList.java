import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void markTask(int index) throws WaguriException {
        if (index < 1 || index > tasks.size()) {
            throw new WaguriException("INVALID TASK NUMBER!");
        }
        tasks.get(index - 1).markAsDone();
    }

    public void unmarkTask(int index) throws WaguriException {
        if (index < 1 || index > tasks.size()) {
            throw new WaguriException("INVALID TASK NUMBER!");
        }
        tasks.get(index - 1).unmark();
    }

    public void deleteTask(int index) throws WaguriException {
        if (index < 1 || index > tasks.size()) {
            throw new WaguriException("INVALID TASK NUMBER!");
        }
        tasks.remove(index - 1);
    }

    public void createTodo(String description) throws WaguriException {
        if (description.trim().isEmpty()) {
            throw new WaguriException("Sir! A todo needs a description.");
        }
        tasks.add(new Todo(description));
    }

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

    public void createEvent(String input) throws WaguriException {
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new WaguriException("SOLDIER! An event needs /from and /to times.");
        }

        String[] parts = input.split("/from|/to");
        if (parts.length < 3) {
            throw new WaguriException("SOLDIER! Usage: event [task] /from [start] /to [end]");
        }

        String description = parts[0].trim();
        String fromString = parts[1].trim();
        String toString = parts[2].trim();

        LocalDateTime from = DateParser.parse(fromString);
        LocalDateTime to = DateParser.parse(toString);
        tasks.add(new Event(description, from, to));
    }

    public ArrayList<Task> getDueTasks(String dateString) throws WaguriException {
        ArrayList<Task> dueTasks = new ArrayList<>();
        LocalDateTime targetDate = DateParser.parse(dateString);

        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getBy().toLocalDate().equals(targetDate.toLocalDate())) {
                    dueTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.getFrom().toLocalDate().equals(targetDate.toLocalDate())) {
                    dueTasks.add(task);
                }
            }
        }

        return dueTasks;
    }

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

}