package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;

public class LoginEngine extends Engine 
{
    private String userID;
    private String userPassword;
    private String targetID; // chiedi login se target = user, chiedi dati sottoposto se user != target  

    public LoginEngine(String userID, String userPassword, String targetID) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.targetID = targetID;
    }

    private String getDataAsUser()
    {
        try (Connection connection = connectDB(dbUrl, "sa", "")) {
            String query = "SELECT userName, cityOfResidence, birthYear, role, organization FROM users WHERE userID = ? AND userPassword = ?"; // // userName -> userID
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            ResultSet result = statement.executeQuery();
            if(result.next())
            {
                return new LoginReply(
                    true, 
                    result.getString("userName"),
                    result.getString("cityOfResidence"),
                    result.getInt("birthYear"),
                    result.getString("role"),
                    result.getString("organization")
                ).toJSONString();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new LoginReply(false, "", "", 0, "", "").toJSONString();
    }

    private String getDataAsConfigurator()
    {
        try (Connection connection = connectDB(dbUrl, "sa", "")) {
            // Verifica che l'utente sia CONFIGURATOR
            String query = "SELECT role, organization FROM users WHERE userID = ? AND userPassword = ?"; // // userName -> userID
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            ResultSet result = statement.executeQuery();
            if(!result.next() || !result.getString("role").equals("CONFIGURATOR"))
            {
                return new LoginReply(false).toJSONString();
            }
            
            String configuratorOrganization = result.getString("organization");
            
            // Prendi dati del target
            String userDataQuery = "SELECT userName, cityOfResidence, birthYear, role, organization FROM users WHERE userID = ?"; // // userName -> userID
            PreparedStatement userStatement = connection.prepareStatement(userDataQuery); // // PreparedStatement
            userStatement.setString(1, targetID);
            ResultSet userResult = userStatement.executeQuery(); // // userStatement.executeQuery()
            
            if(!userResult.next() || !userResult.getString("organization").equals(configuratorOrganization)) // // Controlla userResult.next()
            {
                return new LoginReply(false).toJSONString();
            }
            
            return new LoginReply(
                true, 
                userResult.getString("userName"),
                userResult.getString("cityOfResidence"),
                userResult.getInt("birthYear"), // // "birthYear" tra virgolette
                userResult.getString("role"),
                userResult.getString("organization")
            ).toJSONString();
        } catch(Exception e) {
            e.printStackTrace();
            return new LoginReply(false).toJSONString();
        }
    }
    
    public String handleRequest() // // Rimuovi throws SQLException
    {
        if(userID.equals(targetID))
        {
            return getDataAsUser();
        }
        return getDataAsConfigurator();
    }
}