import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Storage {
    private String filePath;

    public Storage(String filepath) {
        this.filePath = filepath;
        doesDirectoryExists();
    }

    private void doesDirectoryExists() {
        try {
            File file = new File(filePath);
            File parentDirectory = file.getParentFile();

            if (!parentDirectory.exists()) {
                boolean directory_Created = parentDirectory.mkdirs();
                if (directory_Created) {
                    System.out.println("Created data directory: " + parentDirectory.getPath());
                }
            }
            if (!file.exists()) {
                boolean file_created = file.createNewFile();
                if (file_created) {
                    System.out.println("Created file: " + file.getPath());
                }
            }
        } catch (IOException e) {
            System.out.println("Error creating directory/file: " + e.getMessage());
        }
    }

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

    private Task parseTaskFromString(String line) {
        try {
            line = line.trim();
            String type = line.substring(1, 2);
            boolean isDone = line.substring(4, 5).equals("X");
            String remaining = line.substring(7).trim();

            switch (type) {
                case "T":
                    Todo todo = new Todo(remaining);
                    if (isDone) todo.markAsDone();
                    return todo;

                case "D":
                    if (remaining.contains("(by:")) {
                        int byIndex = remaining.indexOf("(by:");
                        String description = remaining.substring(0, byIndex).trim();
                        String by = remaining.substring(byIndex + 4, remaining.length() - 1).trim();

                        Deadline deadline = new Deadline(description, by);
                        if (isDone) deadline.markAsDone();
                        return deadline;
                    }
                    break;

                case "E":
                    if (remaining.contains("(from:") && remaining.contains("to:")) {
                        int fromIndex = remaining.indexOf("(from:");
                        String description = remaining.substring(0, fromIndex).trim();

                        String timePart = remaining.substring(fromIndex + 6, remaining.length() - 1).trim();
                        String[] timeParts = timePart.split(" to:");

                        if (timeParts.length >= 2) {
                            String from = timeParts[0].trim();
                            String to = timeParts[1].trim();

                            Event event = new Event(description, from, to);
                            if (isDone) event.markAsDone();
                            return event;
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error parsing task from: " + line + " - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Failed to parse: " + line);
        return null;
    }

}




// save tasks
// load tasks
// Parse tasks from text format (in Storage class)
