import java.util.Scanner;
import java.util.ArrayList;

public class Leroy {
    public static void main(String[] args) {
        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
        System.out.println("HEY SOLDIER! I'm LEROY - READY FOR ACTION!");
        System.out.println(" What can I do for you today?");
        System.out.println("═╦══════════════════════════════════════════════════════════╦═");

        Scanner input = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();

        while (true) {
            String user_expression = input.nextLine();

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

            System.out.println("═╦══════════════════════════════════════════════════════════╦═");
            System.out.println("📢 RECEIVED: " + user_expression);
            System.out.println("═╦══════════════════════════════════════════════════════════╦═");
            list.add(user_expression);
        }
    }
}