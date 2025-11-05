package Server.Engine;

import java.sql.*;
import org.json.*;
import java.util.*;

import RequestReply.ComunicationType.*;
import RequestReply.Request.*;
import RequestReply.Reply.*;


public class GetEventWithDisponibilityEngine extends Engine
{ 

    private String userID;
    private String userPassword;
    private GetEventReply response = new GetEventReply();

    public GetEventWithDisponibilityEngine
    (
        String userID,
        String userPassword
    ) {
        this.userID = userID;
        this.userPassword = userPassword;
    }

    public String handleRequest()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "")
        ) {
            String query = """
                SELECT e.* 
                FROM events e
                JOIN eventsVoluntaries v ON e.eventName = v.eventName
                JOIN users u ON u.userID = v.userID AND u.userPassword = ?
                WHERE v.userID = ? ;
            """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userPassword);
            statement.setString(2, userID);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                response.insertEvent(
                    resultSet.getString("eventName"),
                    resultSet.getString("description"),
                    resultSet.getString("city"),
                    resultSet.getString("address"),
                    resultSet.getString("meetingPoint"),
                    resultSet.getInt("startDate"),
                    resultSet.getInt("endDate"),
                    resultSet.getString("organization"),
                    resultSet.getInt("minimumUsers"),
                    resultSet.getInt("maximumUsers"),
                    resultSet.getInt("maximumFriends"),
                    resultSet.getString("visitType"),
                    resultSet.getString("state")
                );
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return response.toJSONString();
    }
}