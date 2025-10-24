package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;

public class LoginEngine extends Engine 
{
    private String userID;
    private String userPassword;
    private boolean ccorrectCredentials;

    public LoginEngine
    (
        String userID,
        String userPassword
    )
    {
        this.userID = userID;
        this.userPassword = userPassword;
        this.ccorrectCredentials = canLogIn();
    }

    public boolean canLogIn()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        )
        {
            String query = "SELECT role FROM users WHERE userID='%s' AND userPassword='%s'";
            query = String.format(query, userID, userPassword);
            Statement statement = connection.createStatement();
            if(statement.executeUpdate(query) == 1)
            {
                return true;
            }
        } catch(Exception e)
        {
            return false;
        }
        return false;
    }

    String handleRequest() throws SQLException
    {
        return new LoginReply(canLogIn()).toJSONString();
    }
}
