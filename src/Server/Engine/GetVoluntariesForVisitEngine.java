package Server.Engine;

import java.sql.*;
import java.util.*;
import RequestReply.Reply.*;
import java.util.*;
import RequestReply.UserRoleTitle.*;
import RequestReply.ComunicationType.*;

public class GetVoluntariesForVisitEngine extends Engine
{
    private String eventName;
    
    public GetVoluntariesForVisitEngine(String eventName) {
        this.eventName = eventName;
    }
    
    public String handleRequest()
    {
        ArrayList<String> userIDs = new ArrayList<>();
        
        try (Connection connection = connectDB(dbUrl, "sa", "")) {
            String query = "SELECT userID FROM eventsVoluntariers WHERE eventName = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, eventName);
            
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