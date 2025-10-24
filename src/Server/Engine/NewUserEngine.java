package Server.Engine;

import java.sql.*;
import java.util.*;
import java.util.function.*;

class NewUserEngine 
{

}

/*
class NewUserEngine extends LoginEngine
{
    protected static boolean insertUser(
                String userID, 
                String userPassword, 
                String role
            ) throws SQLException {
        
        String sql = "INSERT INTO users (userID, userPassword, Role) VALUES (?, ?, ?)";
        try 
        (
            Connection connection = connectDB(dbUrl, "sa", "");
            PreparedStatement statement = connection.prepareStatement(sql)
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

    String handleRequest()
    {
        Connection connection = connectDB(dbUrl, "sa", "");
        return true;
    }
}
*/