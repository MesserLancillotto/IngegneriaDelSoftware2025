package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;

public class GetUserDataEngine extends Engine 
{
    private String userID;
    private String userPassword;
    private String targetID; // chiedi login se target = user, chiedi dati sottoposto se user != target  

    public GetUserDataEngine(String userID, String userPassword, String targetID) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.targetID = targetID;
    }

    private String getDataAsUser()
    {
        try (Connection connection = connectDB(dbUrl, "sa", "")) {
            String query = "SELECT userName, cityOfResidence, birthYear, role, organization FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            ResultSet result = statement.executeQuery();
            if(result.next())
            {
                return new GetUserDataReply(
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
        return new GetUserDataReply(false).toJSONString();
    }

    private String getDataAsConfigurator()
    {
        try (Connection connection = connectDB(dbUrl, "sa", "")) {
            String query = "SELECT role, organization FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            ResultSet result = statement.executeQuery();
            if(!result.next() || !result.getString("role").equals("CONFIGURATOR"))
            {
                return new GetUserDataReply(false).toJSONString();
            }
            String configuratorOrganization = result.getString("organization");
            String userDataQuery = "SELECT userName, cityOfResidence, birthYear, role, organization FROM users WHERE userID = ?";
            PreparedStatement userStatement = connection.prepareStatement(userDataQuery);
            userStatement.setString(1, targetID);
            ResultSet userResult = userStatement.executeQuery();
            if(!userResult.next() || !userResult.getString("organization").equals(configuratorOrganization))
            {
                return new GetUserDataReply(false).toJSONString();
            }
            return new GetUserDataReply(
                true, 
                userResult.getString("userName"),
                userResult.getString("cityOfResidence"),
                userResult.getInt("birthYear"),
                userResult.getString("role"),
                userResult.getString("organization")
            ).toJSONString();
        } catch(Exception e) {
            e.printStackTrace();
            return new GetUserDataReply(false).toJSONString();
        }
    }
    
    public String handleRequest()
    {
        if(userID.equals(targetID))
        {
            return getDataAsUser();
        }
        return getDataAsConfigurator();
    }
}