import java.util.Scanner;

public class Leroy {
    public static void main(String[] args) {
        System.out.println("═╦══════════════════════════════════════════════════════════╦═");
        System.out.println("HEY SOLDIER! I'm LEROY - READY FOR ACTION!");
        System.out.println(" What can I do for you today?");
        System.out.println("═╦══════════════════════════════════════════════════════════╦═");

        Scanner input = new Scanner(System.in);

        while (true) {
            String user_expression = input.nextLine();

            if (user_expression.equalsIgnoreCase("bye")) {
                System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("═╦══════════════════════════════════════════════════════════╦═");
                break;
            }

            System.out.println("═╦══════════════════════════════════════════════════════════╦═");
            System.out.println("📢 RECEIVED: " + user_expression);
            System.out.println("═╦══════════════════════════════════════════════════════════╦═");
        }
    }
}