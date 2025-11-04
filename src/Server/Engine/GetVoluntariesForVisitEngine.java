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
    private Map<String, Object> filters;
    
    public GetVoluntariesForVisitEngine(
        String userID, 
        String userPassword, 
        String eventName,
        Map<String, Object> filters
    ) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.eventName = eventName;
        this.filters = filters;
    }
    
    public String handleRequest()
    {
        ArrayList<String> userIDs = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = connectDB(dbUrl, "sa", "")) 
        {
            if(filters.containsKey("eventName"))
            {
                resultSet = byUserID(connection);
            } else {
                resultSet = byParameters(connection);
            }

            while (resultSet.next()) {
                String userID = resultSet.getString("userID");
                userIDs.add(userID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new GetVoluntariesForVisitReply(true, userIDs).toJSONString();
    }

    private ResultSet byUserID(Connection connection)
    {
        ResultSet resultSet;
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
        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            statement.setString(3, eventName);

            resultSet = statement.executeQuery();
        } catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return resultSet;
    }

    private ResultSet byParameters(Connection connection)
    {
        List<Object> parameters = new ArrayList<>();
        ResultSet resultSet;
        StringBuilder query = new StringBuilder("""
        SELECT DISTINCT u.userID 
        FROM users u
        JOIN allowedVisits av ON u.userID = av.userID
        JOIN users authUser ON authUser.userID = ? AND authUser.userPassword = ?
        WHERE u.organization = authUser.organization """);
        try
        {
            parameters.add(userID);
            parameters.add(userPassword);

            if(filters.containsKey("city"))
            {
                query.append(" AND u.city = ? ");
                parameters.add((String)filters.get("city"));
            }
            if(filters.containsKey("year"))
            {
                query.append(" AND (CASE WHEN ? = true THEN u.birthYear > ? ELSE u.birthYear <= ? END) ");
                parameters.add((Integer)filters.get("year"));
                parameters.add((Boolean)filters.get("olderThanYear"));
                parameters.add((Boolean)filters.get("olderThanYear"));
            }
            if(filters.containsKey("visitType"))
            {
                query.append(" AND av.visitType LIKE ? ");
                parameters.add((String)filters.get("visitType"));
            }
            if(filters.containsKey("organization"))
            {
                query.append(" AND u.organization = ? ");
                parameters.add((String)filters.get("organization"));
            }
            query.append(";");
            PreparedStatement statement = connection.prepareStatement(query.toString());
            for(int i = 0; i < parameters.size(); i++)
            {
                statement.setObject(i + 1, parameters.get(i));
            }
            resultSet = statement.executeQuery();
        } catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return resultSet;
    }
}