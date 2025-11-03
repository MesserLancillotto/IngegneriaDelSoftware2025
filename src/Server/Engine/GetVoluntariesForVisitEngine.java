package Server.Engine;

import java.sql.*;
import java.util.*;
import RequestReply.Reply.*;
import java.util.*;
import RequestReply.UserRoleTitle.*;
import RequestReply.ComunicationType.*;

public class GetVoluntariesForVisitEngine extends Engine
{
    private String userID;
    private String userPassword;
    private String eventName;
    
    public GetVoluntariesForVisitEngine(
        String userID, 
        String userPassword, 
        String eventName
    ) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.eventName = eventName;
    }
    
    public String handleRequest()
    {
        ArrayList<String> userIDs = new ArrayList<>();
        
        try (Connection connection = connectDB(dbUrl, "sa", "")) 
        {
            String query = """
                SELECT ev.userID 
                FROM eventsVoluntaries ev, users u1
                WHERE u1.userID = ?
                AND u1.userPassword = ?
                AND ev.eventName = ?
                AND EXISTS (
                    SELECT 1 FROM users u2 
                    WHERE u2.organization = u1.organization 
                    AND u2.userID = ev.userID
                );
            """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            statement.setString(3, eventName);
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String userID = resultSet.getString("userID");
                userIDs.add(userID);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new GetVoluntariesForVisitReply(true, userIDs).toJSONString();
    }
}