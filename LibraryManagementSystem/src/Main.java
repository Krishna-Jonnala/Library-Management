import java.util.Scanner;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Test database connection first
        try {
            Connection testConn = DBConnection.getConnection();
            testConn.close();
            System.out.println("✅ Connected to Database Successfully!\n");
        } catch (Exception e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
            System.exit(1);
        }

        LibraryManager manager = new LibraryManager();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. View Books");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
            } catch (Exception e) {
                System.out.println("❌ Please enter a valid number!");
                sc.nextLine(); // clear the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    manager.addBook(title, author);
                    break;

                case 2:
                    System.out.print("Enter Book ID to issue: ");
                    int issueId = sc.nextInt();
                    manager.issueBook(issueId);
                    break;

                case 3:
                    System.out.print("Enter Book ID to return: ");
                    int returnId = sc.nextInt();
                    manager.returnBook(returnId);
                    break;

                case 4:
                    manager.viewBooks();
                    break;

                case 5:
                    System.out.println("👋 Exiting... Goodbye!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }

            System.out.println(); // adds a blank line for spacing
        }
    }
}
