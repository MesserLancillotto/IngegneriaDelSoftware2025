package Server.Engine;

import java.sql.*;

class Engine
{
    public static void main(String[] args) {
        // Database PERSISTENTE nella home directory
        String url = "jdbc:h2:~/documents/IngegneriaDelSoftware2025/Databases/USERS";

        try 
        (
            Connection conn = DriverManager.getConnection(url, "sa", "password123")
        ) {
            System.out.println("Connesso a database PERSISTENTE");
            createTableIfNotExists(conn);
            insertUsers(conn);
            showAllUsers(conn);
            System.out.println("\nI dati sono stati salvati su disco!");
            System.out.println("Riesegui il programma per vedere che i dati sono ancora lì");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "userID VARCHAR(50) PRIMARY KEY, " +
                     "userPassword VARCHAR(100), " +
                     "Role VARCHAR(20))";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabella users verificata/creata");
        }
    }
    
    private static void insertUsers(Connection conn) throws SQLException {
        // Inserisce solo se non esistono già
        String sql = "MERGE INTO users (userID, userPassword, Role) KEY(userID) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String[][] users = {
                {"Pippo", "password123", "ADMIN"},
                {"Pluto", "asdfasdf", "USER"},
                {"Paperino", "safePassword1+", "USER"}
            };
            
            for (String[] user : users) {
                pstmt.setString(1, user[0]);
                pstmt.setString(2, user[1]);
                pstmt.setString(3, user[2]);
                pstmt.executeUpdate();
            }
            System.out.println("Utenti inseriti/aggiornati");
        }
    }
    
    private static void showAllUsers(Connection conn) throws SQLException {
        String sql = "SELECT userID, userPassword, Role FROM users";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\nTutti gli utenti nel database:");
            System.out.println("--------------------------------");
            
            while (rs.next()) {
                String userID = rs.getString("userID");
                String password = rs.getString("userPassword");
                String role = rs.getString("Role");
                
                System.out.printf("ID: %-10s Password: %-15s Role: %s%n", 
                                userID, password, role);
            }
        }
    }
}
