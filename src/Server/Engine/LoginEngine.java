
package Server.Engine;

import java.sql.*;
import RequestReply.Reply.LoginReply;

class LoginEngine extends Engine 
{
    private String userID;
    private String userPassword;

    public LoginEngine
    (
        String userID,
        String userPassword
    )
    {
        this.userID = userID;
        this.userPassword = userPassword;
    }

    boolean canLogIn()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        )
        {
            String query = "SELECT role FROM users WHERE userID='%s' AND userPassword='%s'";
            query = String.format(query, userID, userPassword);
            Statement statement = connection.createStatement()                
        } catch(Exception e)
        {
            return false;
        }
    }

    String handleRequest() throws SQLException
    {
        return new LoginReply(canLogIn()).toJSONString();
    }
}
