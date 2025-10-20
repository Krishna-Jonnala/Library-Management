import java.sql.*;

public class LibraryManager {

    // Add a new book
    public void addBook(String title, String author) {
        String sql = "INSERT INTO books (title, author, is_issued) VALUES (?, ?, FALSE)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.executeUpdate();
            System.out.println("✅ Book added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Issue a book
    public void issueBook(int id) {
        String sql = "UPDATE books SET is_issued = TRUE WHERE id = ? AND is_issued = FALSE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("📘 Book issued successfully!");
            else System.out.println("❌ Book is already issued or ID not found!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Return a book
    public void returnBook(int id) {
        String sql = "UPDATE books SET is_issued = FALSE WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("📗 Book returned successfully!");
            else System.out.println("❌ Invalid book ID!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View all books
    public void viewBooks() {
        String sql = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== 📚 Library Books ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Title: %s | Author: %s | Issued: %s%n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("is_issued") ? "Yes" : "No");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
