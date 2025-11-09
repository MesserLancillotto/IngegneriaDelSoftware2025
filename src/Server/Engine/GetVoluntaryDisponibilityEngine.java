package Server.Engine;

import java.sql.*;
import java.util.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import RequestReply.Reply.*;

public class GetVoluntaryDisponibilityEngine extends Engine
{
    private String userID;
    private String userPassword;
    private String targetID;
    
    public GetVoluntaryDisponibilityEngine(
        String userID, 
        String userPassword, 
        String targetID
    ) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.targetID = targetID;
    }
    
    public String handleRequest()
    {
        try 
        (
            Connection connection = connectDB(dbUrl, "sa", "")
        ) {
            String roleCheckQuery = "SELECT role, organization FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement roleStatement = connection.prepareStatement(roleCheckQuery);
            roleStatement.setString(1, userID);
            roleStatement.setString(2, userPassword);
            ResultSet roleResult = roleStatement.executeQuery();

            if(!roleResult.next() || !roleResult.getString("role").equals("CONFIGURATOR")) {
                return new GetVoluntaryDisponibilityReply(new ArrayList<Map.Entry<String, Integer>>()).toJSONString(); 
            }

            String query = """
                SELECT eventName, time FROM eventsVoluntaries 
                WHERE userID = ? 
            """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, targetID);
            ResultSet result = statement.executeQuery();

            ArrayList<Map.Entry<String, Integer>> events = new ArrayList<>();
            
            while(result.next()) {
                String eventName = result.getString("eventName");
                int time = result.getInt("time");
                events.add(new AbstractMap.SimpleEntry<>(eventName, time));
            }
            return new GetVoluntaryDisponibilityReply(events).toJSONString();
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return new GetVoluntaryDisponibilityReply(new ArrayList<Map.Entry<String, Integer>>()).toJSONString(); 
    }
}