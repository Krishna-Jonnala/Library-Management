import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    public static void main(String[] args) {
        try {
            // First try to connect without database name to create the database
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            
            // Create database if it doesn't exist
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS library_db");
            stmt.executeUpdate("USE library_db");
            
            // Drop tables in correct order
            stmt.executeUpdate("DROP TABLE IF EXISTS loans");
            stmt.executeUpdate("DROP TABLE IF EXISTS members");
            stmt.executeUpdate("DROP TABLE IF EXISTS books");
            
            // Create books table
            String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "title VARCHAR(255) NOT NULL, " +
                "author VARCHAR(255), " +
                "is_issued BOOLEAN DEFAULT FALSE)";
            stmt.executeUpdate(createBooksTable);
            
            // Create members table
            String createMembersTable = "CREATE TABLE IF NOT EXISTS members (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255), " +
                "phone VARCHAR(20))";
            stmt.executeUpdate(createMembersTable);
            
            // Create loans table
            String createLoansTable = "CREATE TABLE IF NOT EXISTS loans (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "book_id INT, " +
                "member_id INT, " +
                "loan_date DATE, " +
                "due_date DATE, " +
                "return_date DATE, " +
                "FOREIGN KEY (book_id) REFERENCES books(id), " +
                "FOREIGN KEY (member_id) REFERENCES members(id))";
            stmt.executeUpdate(createLoansTable);
            
            // Insert some sample data
            stmt.executeUpdate("INSERT INTO books (title, author, is_issued) VALUES " +
                "('The Great Gatsby', 'F. Scott Fitzgerald', FALSE), " +
                "('To Kill a Mockingbird', 'Harper Lee', FALSE)");
                
            System.out.println("✅ Database and tables created successfully!");
            stmt.executeUpdate("INSERT INTO members (name, email) VALUES " +
                "('John Doe', 'john@example.com'), " +
                "('Jane Smith', 'jane@example.com')");
            
            System.out.println("✅ Sample data inserted successfully!");
            
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Database setup failed:");
            e.printStackTrace();
        }
    }
}