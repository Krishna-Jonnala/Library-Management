import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String BASE_URL = "jdbc:mysql://localhost:3306";
    private static final String DB_NAME = "library_db";
    private static final String URL = BASE_URL + "/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root"; // your MySQL username
    private static final String PASSWORD = "Srm.9505744454"; // MySQL password

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            // Explicitly load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            try {
                // First try to connect to the specific database
                return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                // If database doesn't exist, create it
                Connection baseConn = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
                Statement stmt = baseConn.createStatement();
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
                stmt.close();
                baseConn.close();
                
                // Now connect to the new database
                return DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Make sure you have the MySQL connector JAR in your project.");
            throw e;
        } catch (SQLException e) {
            System.err.println("Database connection failed! Please check:");
            System.err.println("1. Is MySQL server running?");
            System.err.println("2. Are the username and password correct?");
            System.err.println("3. Is MySQL listening on port 3306?");
            throw e;
        }
    }
}
