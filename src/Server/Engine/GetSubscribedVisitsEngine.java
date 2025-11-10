package Server.Engine;

import java.sql.*;
import org.json.*;
import java.util.*;

import RequestReply.ComunicationType.*;
import RequestReply.Request.*;
import RequestReply.Reply.*;

public class GetSubscribedVisitsEngine extends Engine
{
    private Map<String, Object> filters;
    private String userID;
    private String password;

    public GetSubscribedVisitsEngine(String userID, String password, Map<String, Object> filters) {
        this.userID = userID;
        this.password = password;
        this.filters = filters;
    }
    
    public String handleRequest()
    {
        GetEventReply response = new GetEventReply();
        
        try (Connection connection = connectDB(dbUrl, "sa", "")) 
        {
            
            String  q = "SELECT * FROM users WHERE userID = ? AND userPassword = ? AND role = 'USER'";
            PreparedStatement s = connection.prepareStatement(q);
            s.setString(1, userID);
            s.setString(2, password);
            if(!s.executeQuery().next())
            {
                return response.toJSONString();
            }

            StringBuilder query = new StringBuilder("""
            SELECT eventName, eventDescription, city, address, meetingPoint, startDate, endDate, organization, minUsers, maxUsers,  maxFriends, visitType, state, ticketPrice, voluntariesCanSubmit, usersCanSubmit
                FROM events e
                INNER JOIN eventsUsers eu ON e.eventName = eu.event AND eu.userID = ? 
            """);
            List<Object> parameters = new ArrayList<>();
            parameters.add(userID);

            if (filters.containsKey("partialName"))
            {
                query.append(" AND eventName LIKE ? ");
                parameters.add(filters.get("%" + "partialName" + "%"));
            }
            if (filters.containsKey("city")) 
            {
                query.append(" AND city = ? ");
                parameters.add(filters.get("city"));
            }
            if (filters.containsKey("address")) 
            {
                query.append(" AND address LIKE ? ");
                parameters.add("%" + filters.get("address") + "%");
            }
            if (filters.containsKey("organization")) 
            {
                query.append(" AND organization = ? ");
                parameters.add(filters.get("organization"));
            }
            if (filters.containsKey("visitType")) 
            {
                query.append(" AND visitType = ? ");
                parameters.add(filters.get("visitType"));
            }
            if (filters.containsKey("state")) 
            {
                query.append(" AND state = ? ");
                parameters.add(filters.get("state"));
            }
            
            PreparedStatement statement = connection.prepareStatement(query.toString());
            for (int i = 0; i < parameters.size(); i++) 
            {
                statement.setObject(i + 1, parameters.get(i));
            }
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                response.insertEvent(
                    resultSet.getString("eventName"),
                    resultSet.getString("eventDescription"),
                    resultSet.getString("city"),
                    resultSet.getString("address"),
                    resultSet.getString("meetingPoint"),
                    resultSet.getInt("startDate"),
                    resultSet.getInt("endDate"),
                    resultSet.getString("organization"),
                    resultSet.getInt("minUsers"),
                    resultSet.getInt("maxUsers"),
                    resultSet.getInt("maxFriends"),
                    resultSet.getString("visitType"),
                    resultSet.getString("state")
                );
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return response.toJSONString();
    }
}