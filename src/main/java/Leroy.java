import java.util.Scanner;
import java.util.ArrayList;

public class Leroy {
    public static void main(String[] args) {
        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
        System.out.println("HEY SOLDIER! I'm LEROY - READY FOR ACTION!");
        System.out.println(" What can I do for you today?");
        System.out.println("═╦══════════════════════════════════════════════════════════╦═");

        Scanner input = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        while (true) {
            String user_expression = input.nextLine();
            Task task = new Task(user_expression, false);

            if (user_expression.equals("bye")) {
                System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                break;
            }
            if (user_expression.equals("list")) {
                System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                System.out.println("YOUR LIST:");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i));
                }
                System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                continue;
            }

            if (user_expression.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(user_expression.substring(5));
                list.get(taskNumber - 1).markAsDone();
                System.out.println("Nice! I've marked this task as done:\n" + list.get(taskNumber - 1));
                System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                continue;
            }
            if (user_expression.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(user_expression.substring(7));
                list.get(taskNumber - 1).unmark();
                System.out.println("OK, I've marked this task as not done yet:\n" + list.get(taskNumber - 1));
                System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                continue;
            }
            if (user_expression.startsWith("todo ")) {
                String todo_task = user_expression.substring(5);
                Todo TD = new Todo(todo_task, false);
                list.add(TD);
                System.out.println("Got it. I've added this task:\n" + list.getLast() + "\nNow you have " + list.size() + " tasks in the list.\n");
                System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                continue;
            }

            if (user_expression.startsWith("deadline ")) {
                String content = user_expression.substring(9).trim();
                String[] parts = content.split(" /by ", 2);

                if (parts.length < 2) {
                    System.out.println("❌ Please use: deadline [task] /by [time]");
                    return;
                }

                String description = parts[0].trim();
                String deadline = parts[1].trim();
                Deadline TD = new Deadline(description, false, deadline);
                list.add(TD);
                System.out.println("Got it. I've added this task:\n" + list.getLast() + "\nNow you have " + list.size() + " tasks in the list.\n");
                System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                continue;
            }

            if (user_expression.startsWith("event ")) {
                String content = user_expression.substring(6).trim();
                String[] parts = content.split(" /from | /to ");

                if (parts.length < 3) {
                    System.out.println("❌ Please use: event [task] /from [start] /to [end]");
                    return;
                }

                String description = parts[0].trim();
                String start = parts[1].trim();
                String end = parts[2].trim();

                Event TD = new Event(description, start, end);
                list.add(TD);
                System.out.println("Got it. I've added this task:\n" + list.getLast() + "\nNow you have " + list.size() + " tasks in the list.\n");
                System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                continue;
            }





            System.out.println("═╦══════════════════════════════════════════════════════════╦═");
            System.out.println("📢 RECEIVED: " + user_expression);
            System.out.println("═╦══════════════════════════════════════════════════════════╦═");
            list.add(task);
        }
    }
}