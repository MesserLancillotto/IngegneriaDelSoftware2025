package Server.Engine;

import java.sql.*;
import java.util.*;
import java.util.function.*;

public class Engine
{
    protected static String dbUrl = "jdbc:h2:~/documents/IngegneriaDelSoftware2025/databases/MAIN_DB";

    public static void main(String[] args) {
        // MEMENTO args[0] -> prima flag passata
        if(args[0].equals("--terraform"))
        {
            boolean x = terraform();
        }
    }

    private static boolean terraform()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        )
        {
            createUsersTable(connection);
            createOrganizationsTable(connection);
            createEventsTable(connection);
        } catch(Exception e) 
        {
            e.printStackTrace();
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
        String sql = "CREATE TABLE IF NOT EXISTS users (userName VARCHAR(32), cityOfResidence VARCHAR(32), birthYear INT, userID VARCHAR(32) UNIQUE, userPassword VARCHAR(64), role VARCHAR(16))";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw e;
        }
    }

    private static void createOrganizationsTable(
            Connection connection
                ) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS organizations (organizationName VARCHAR(32), territory VARCHAR(64))";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw e;
        }
    }
 
    private static void createEventsTable(
            Connection connection
                ) throws SQLException {
        String eventTableSqlQuery = "CREATE TABLE IF NOT EXISTS events (eventName VARCHAR(64), address VARCHAR(64), date INT)";
        String eventParticipantsSqlQuery = "CREATE TABLE IF NOT EXISTS eventsVoluntaries (eventName VARCHAR(64), userID VARCHAR(32))";
        
        try (Statement statement = connection.createStatement()) {
            statement.execute(eventTableSqlQuery);
            statement.execute(eventParticipantsSqlQuery);
        } catch (SQLException e) {
            throw e;
        }
    }
}