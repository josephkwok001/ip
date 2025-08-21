import java.util.Scanner;
import java.util.ArrayList;

public class Leroy {
    public enum Command {
        TODO,
        DEADLINE,
        EVENT,
        DELETE,
        LIST,
        MARK,
        UNMARK,
        BYE,
        UNKNOWN // For handling invalid commands
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

        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
        System.out.println("HEY SOLDIER! I'm LEROY - READY FOR ACTION!");
        System.out.println(" What can I do for you today?");
        System.out.println("═╦══════════════════════════════════════════════════════════╦═");

        Scanner input = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        while (input.hasNextLine()) {
            try {
                String user_expression = input.nextLine();
                Command command = CommandParser.parseCommand(user_expression);

                switch(command) {
                    case BYE:
                        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                        System.out.println("Bye. Hope to see you again soon!");
                        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                        break;
                    case LIST:
                        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                        System.out.println("YOUR LIST:");
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println((i + 1) + ". " + list.get(i));
                        }
                        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                        continue;
                    case MARK:
                        int taskNumber = Integer.parseInt(user_expression.substring(5));
                        list.get(taskNumber - 1).markAsDone();
                        System.out.println("Nice! I've marked this task as done:\n" + list.get(taskNumber - 1));
                        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                        continue;
                    case UNMARK:
                        int unmark_taskNumber = Integer.parseInt(user_expression.substring(7));
                        list.get(unmark_taskNumber - 1).unmark();
                        System.out.println("OK, I've marked this task as not done yet:\n" + list.get(unmark_taskNumber - 1));
                        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                        continue;
                    case TODO:
                        if (user_expression.trim().equalsIgnoreCase("todo")) {
                            throw new LeroyException("SOLDIER! A todo needs a description. Usage: todo [task]");
                        }

                        String todo_task = user_expression.substring(5);
                        Todo TD = new Todo(todo_task, false);
                        list.add(TD);
                        System.out.println("Got it. I've added this task:\n" + list.getLast() + "\nNow you have " + list.size() + " tasks in the list.\n");
                        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                        continue;
                    case DEADLINE:
                        if (user_expression.trim().equalsIgnoreCase("deadline")) {
                            throw new LeroyException("SOLDIER! A deadline needs details. Usage: deadline [task] /by [time]");
                        }

                        String content = user_expression.substring(9).trim();
                        String[] parts = content.split(" /by ", 2);

                        if (parts.length < 2) {
                            System.out.println("❌ Please use: deadline [task] /by [time]");
                            return;
                        }

                        String description = parts[0].trim();
                        String deadline = parts[1].trim();
                        Deadline d = new Deadline(description, false, deadline);
                        list.add(d);
                        System.out.println("Got it. I've added this task:\n" + list.getLast() + "\nNow you have " + list.size() + " tasks in the list.\n");
                        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                        continue;
                    case EVENT:
                        if (user_expression.trim().equalsIgnoreCase("event")) {
                            throw new LeroyException("SOLDIER! An event needs details. Usage: event [task] /from [start] /to [end]");
                        }

                        String content_event = user_expression.substring(6).trim();
                        String[] parts_event = content_event.split(" /from | /to ");

                        if (parts_event.length < 3) {
                            System.out.println("❌ Please use: event [task] /from [start] /to [end]");
                            return;
                        }

                        String description_event = parts_event[0].trim();
                        String start = parts_event[1].trim();
                        String end = parts_event[2].trim();

                        Event e = new Event(description_event, start, end);
                        list.add(e);
                        System.out.println("Got it. I've added this task:\n" + list.getLast() + "\nNow you have " + list.size() + " tasks in the list.\n");
                        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                        continue;

                    case DELETE:
                        if (user_expression.trim().equalsIgnoreCase("delete")) {
                            throw new LeroyException("SOLDIER! You have not specified which event that needs to be deleted");
                        }

                        int index = Integer.parseInt(user_expression.substring(7));

                        if (index < 1 || index > list.size()) {
                            throw new LeroyException("INVALID TASK NUMBER!");
                        }

                        System.out.println("Noted. I've removed this task:\n");
                        System.out.println(list.get(index - 1));
                        list.remove(index - 1);
                        System.out.println("Now you have " + list.size() +" tasks in the list.");
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
                        throw new LeroyException("COMMAND NOT RECOGNIZED! Available commands:" + commands.toString());
                }
            } catch (LeroyException e) {
                System.out.println("____________________________________________________________");
                System.out.println("⚠️  " + e.getMessage());
                System.out.println("____________________________________________________________");
            } catch (Exception e) {
                System.out.println("____________________________________________________________");
                System.out.println("⚠️  Unexpected Error" + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }

    }

    static class LeroyException extends Exception {
        public LeroyException(String message) {
            super(message);
        }
    }

}