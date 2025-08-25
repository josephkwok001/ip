import java.util.Scanner;
import java.util.ArrayList;

public class Waguri {
    private static final String BORDER = "═╦══════════════════════════════════════════════════════════╦═";
    public static void printBorder() {
        System.out.println(BORDER);
    }

    public enum Command {
        TODO,
        DEADLINE,
        EVENT,
        DELETE,
        LIST,
        MARK,
        UNMARK,
        BYE,
        UNKNOWN
    }

    public static class CommandParser {
        public static Command parseCommand(String input) {
            if (input == null || input.trim().isEmpty()) {
                return Command.UNKNOWN;
            }

            String[] parts = input.split(" ", 2);
            String commandWord = parts[0].toUpperCase();

            try {
                return Command.valueOf(commandWord);
            } catch (IllegalArgumentException e) {
                return Command.UNKNOWN;
            }
        }
    }


    public static void main(String[] args) {
        Storage storage = new Storage("./data/waguri.txt");
        ArrayList<Task> taskList = storage.loadTasks();
        System.out.println(taskList);

        printBorder();
        System.out.println("Hiiii, I'm your personal chat bot Waguri");
        System.out.println(" What can I do for you today?");
        printBorder();

        Scanner input = new Scanner(System.in);

        while (input.hasNextLine()) {
            try {
                String userExpression = input.nextLine();
                Command command = CommandParser.parseCommand(userExpression);

                switch (command) {
                case BYE:
                    printBorder();
                    System.out.println("Bye. Hope to see you again soon!");
                    printBorder();
                    break;
                case LIST:
                    printBorder();
                    System.out.println("YOUR LIST:");
                    for (int i = 0; i < taskList.size(); i++) {
                        System.out.println((i + 1) + ". " + taskList.get(i));
                    }
                    printBorder();
                    continue;
                case MARK:
                    int taskNumber = Integer.parseInt(userExpression.substring(5));
                    taskList.get(taskNumber - 1).markAsDone();
                    System.out.println("Nice! I've marked this task as done:\n" + taskList.get(taskNumber - 1));
                    printBorder();
                    storage.saveTasks(taskList);
                    continue;
                case UNMARK:
                    int unmarkTaskNumber = Integer.parseInt(userExpression.substring(7));
                    taskList.get(unmarkTaskNumber - 1).unmark();
                    System.out.println("OK, I've marked this task as not done yet:\n"
                            + taskList.get(unmarkTaskNumber - 1));
                    storage.saveTasks(taskList);
                    printBorder();

                    continue;
                case TODO:
                    if (userExpression.trim().equalsIgnoreCase("todo")) {
                        throw new WaguriException("Sir! A todo needs a description. Usage: todo [task]");
                    }
                    String todoTask = userExpression.substring(5);
                    Todo todo = new Todo(todoTask);
                    taskList.add(todo);
                    storage.saveTasks(taskList);
                    System.out.println("Got it. I've added this task:\n" + taskList.getLast()
                            + "\nNow you have "
                            + taskList.size()
                            + " tasks in the list.\n");
                    printBorder();

                    continue;
                case DEADLINE:
                    if (userExpression.trim().equalsIgnoreCase("deadline")) {
                        throw new WaguriException("Sir! A deadline needs details. "
                                + "Usage: deadline [task] /by [time]");
                    }

                    String content = userExpression.substring(9).trim();
                    String[] parts = content.split(" /by ", 2);

                    if (parts.length < 2) {
                        System.out.println("❌ Please use: deadline [task] /by [time]");
                        continue;
                    }

                    String description = parts[0].trim();
                    String deadline = parts[1].trim();
                    Deadline deadlineTask = new Deadline(description, deadline);
                    taskList.add(deadlineTask);
                    storage.saveTasks(taskList);
                    System.out.println("Got it. I've added this task:\n"
                            + taskList.getLast()
                            + "\nNow you have "
                            + taskList.size()
                            + " tasks in the list.\n");
                    printBorder();
                    continue;
                case EVENT:
                    if (userExpression.trim().equalsIgnoreCase("event")) {
                        throw new WaguriException("SOLDIER! An event needs details. "
                                + "Usage: event [task] /from [start] /to [end]");
                    }

                    String contentEvent = userExpression.substring(6).trim();
                    String[] partsEvent = contentEvent.split(" /from | /to ");

                    if (partsEvent.length < 3) {
                        System.out.println("❌ Please use: event [task] /from [start] /to [end]");
                        continue;
                    }

                    String descriptionEvent = partsEvent[0].trim();
                    String start = partsEvent[1].trim();
                    String end = partsEvent[2].trim();

                    Event event = new Event(descriptionEvent, start, end);
                    taskList.add(event);
                    storage.saveTasks(taskList);
                    System.out.println("Got it. I've added this task:\n" + taskList.getLast() + "\nNow you have " + taskList.size() + " tasks in the list.\n");
                    printBorder();
                    continue;
                case DELETE:
                    if (userExpression.trim().equalsIgnoreCase("delete")) {
                        throw new WaguriException("SOLDIER! You have not specified which event that needs to be deleted");
                    }

                    int index = Integer.parseInt(userExpression.substring(7));

                    if (index < 1 || index > taskList.size()) {
                        throw new WaguriException("INVALID TASK NUMBER!");
                    }

                    System.out.println("Noted. I've removed this task:\n");
                    System.out.println(taskList.get(index - 1));
                    taskList.remove(index - 1);
                    System.out.println("Now you have "
                            + taskList.size()
                            + " tasks in the list.");
                    continue;
                case UNKNOWN:
                    StringBuilder commands = new StringBuilder();
                    for (Command cmd : Command.values()) {
                        if (cmd != Command.UNKNOWN) {
                            if (commands.length() > 0) {
                                commands.append(", ");
                            }
                            commands.append(cmd.name().toLowerCase());
                        }
                    }
                    throw new WaguriException("COMMAND NOT RECOGNIZED! Available commands:"
                            + commands.toString());
                default:
                    throw new WaguriException("Unexpected command: " + command);
                }
            } catch (WaguriException e) {
                printBorder();
                System.out.println("⚠️  " + e.getMessage());
                printBorder();
            } catch (Exception e) {
                printBorder();
                System.out.println("⚠️  Unexpected Error: " + e.getMessage());
                printBorder();
            }
        }
    }

    static class WaguriException extends Exception {
        public WaguriException(String message) {
            super(message);
        }
    }
}
