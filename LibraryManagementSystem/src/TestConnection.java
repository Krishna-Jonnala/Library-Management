import java.sql.Connection;
import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("✅ Connected to the database!");
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}
