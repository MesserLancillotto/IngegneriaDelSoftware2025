package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import RequestReply.Request.*;

class GetVisitablePlacesEngine extends Engine
{
    private String city;
    private String address; 
    
    public GetVisitablePlacesEngine
    (
        String city,
        String address
    ) {
        this.city = city;
        this.address = address;
    }

    public String handleRequest()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        ) {
            String query = "SELECT * FROM events WHERE (city = ? OR ? IS NULL) AND (address LIKE '%' || ? || '%' OR ? IS NULL)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, city);
            statement.setString(2, city); 
            statement.setString(3, address);
            statement.setString(4, address);
            ResultSet resultSet = statement.executeQuery();
            GetEventReply reply = new GetEventReply();
            while (resultSet.next()) {
                reply.insertEvent(
                    resultSet.getString("eventName"),
                    resultSet.getString("eventDescription"),
                    resultSet.getString("city"),
                    resultSet.getString("address"),
                    resultSet.getInt("startDate"),
                    resultSet.getInt("endDate"),
                    resultSet.getString("organizationName"),
                    resultSet.getInt("minUsers"),
                    resultSet.getInt("maxUsers"),
                    resultSet.getInt("maxFriends"),
                    resultSet.getString("visitType"),
                    resultSet.getBoolean("confirmed")
                );
            }
            return reply.toJSONString();
        } catch(Exception e)
        {
            return "";
        }
    }
}