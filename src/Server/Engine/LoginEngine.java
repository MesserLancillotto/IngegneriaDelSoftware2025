package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;

public class LoginEngine extends Engine 
{
    private String userID;
    private String userPassword;
    private String targetID; // chiedi login se target = user, chiedi dati sottoposto se user != target  

    public LoginEngine
    (
        String userID,
        String userPassword,
        String targetID
    )
    {
        this.userID = userID;
        this.userPassword = userPassword;
        this.asUser = asUser;
    }

    private String getDataAsUser()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        ) {
            String query = "SELECT userName, cityOfResidence, birthYear, role, organization FROM users WHERE userName = ? AND userPassword = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            ResultSet result = statement.executeQuery();
            if(result.next())
            {
                return LoginReply(
                    true, 
                    result.getString("userName"),
                    result.getString("cityOfResidence"),
                    result.getInt(birthYear),
                    result.getString("role"),
                    result.getString("organization")
                ).toJSONString();
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private String getDataAsConfigurator()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        ) {
            String query = "SELECT role, organization FROM users WHERE userName = ? AND userPassword = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            ResultSet result = statement.executeQuery();
            if(!result.next() || !result.getString("role").equals("CONFIGURATOR"))
            {
                return LoginReply(
                    false, 
                    "",
                    "",
                    0,
                    "",
                    ""
                ).toJSONString();
            }
            String userDataQuery = "SELECT userName, cityOfResidence, birthYear, role, organization FROM users WHERE userName = ?";
            Statement userStatement = connection.prepareStatement(userDataQuery);
            userStatement.setString(1, targetID);
            ResultSet userResult = statement.executeQuery();
            if(!userResult.getString("organization").equals(result.getString("organization")))
            {
                return LoginReply(
                    true, 
                    "",
                    "",
                    0,
                    "",
                    ""
                ).toJSONString();
            }
            return LoginReply(
                true, 
                userResult.getString("userName"),
                userResult.getString("cityOfResidence"),
                userResult.getInt(birthYear),
                userResult.getString("role"),
                userResult.getString("organization")
            ).toJSONString();
        } catch(Exception e)
        {
            e.printStackTrace();
            return LoginReply(
                false, 
                "",
                "",
                0,
                "",
                ""
            ).toJSONString();
        }
    }
// String userOrganizationQuery = "SELECT organizations FROM users WHERE userID = ? AND userPassword = ? ";
// PreparedStatement userOrganizationStatement = connection.prepareStatement(userOrganizationQuery);
// userOrganizationStatement.setString(userID);
// userOrganizationStatement.setString(userPassword);

    public String handleRequest() throws SQLException
    {
        String fail = new LoginReply(false).toJSONString();
        try
        {
            String response = new LoginReply(canLogIn()).toJSONString();
            return response;
        }
        catch(Exception e)
        {
            return fail;
        }
    }
}
