package Server.Engine;

import java.sql.*;

public class Engine 
{
    public static String usersDbUrl = "jdbc:h2:~/documents/IngegneriaDelSoftware2025/databases/USERS";
    public static String organizationDbUrl = "jdbc:h2:~/documents/IngegneriaDelSoftware2025/databases/ORGANIZATIONS";
    public static String eventDbUrl = "jdbc:h2:~/documents/IngegneriaDelSoftware2025/databases/EVENTS";

    public static void main(String[] args) {
        // MEMENTO args[0] -> prima flag passata
        boolean x = terraform();

    }

    private static boolean terraform()
    {

        
        for(String url : new String[]{usersDbUrl, organizationDbUrl, eventDbUrl})
        {
            try
            (
                Connection connection = connectDB(url, "sa", "");
            )
            {
                createUsersTable(connection);
            } catch(Exception e) 
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    protected static Connection connectDB(
            String url, 
            String dbUser, 
            String dbUserPassword
                    ) throws SQLException {
        try 
        {
            Connection connection = DriverManager.getConnection(
                url, 
                dbUser, 
                dbUserPassword);
            return connection;
        } catch (SQLException e) 
        {
            throw e;
        }
    }

    private static void createUsersTable(
            Connection connection
                ) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (userID VARCHAR(32) PRIMARY KEY, userPassword VARCHAR(64), Role VARCHAR(16))";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw e;
        }
    }
 
    protected static boolean insertUser(
            Connection conn, 
            String userID, 
            String userPassword, 
            String role
                ) throws SQLException {
        String sql = "INSERT INTO users (userID, userPassword, Role) VALUES (?, ?, ?)";
        try 
        (
            PreparedStatement statement = conn.prepareStatement(sql)
        ) {
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            statement.setString(3, role);
            int rowsAffected = statement.executeUpdate();
            return (rowsAffected > 0);
        } catch(Exception e )
        {
            return false;
        }
    }
}