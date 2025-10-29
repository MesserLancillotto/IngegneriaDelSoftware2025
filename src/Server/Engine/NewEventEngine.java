package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;
import RequestReply.UserRoleTitle.*;
import RequestReply.ComunicationType.*;

public class NewEventEngine extends Engine
{

    private String eventName;
    private String description;
    private String city;
    private String address;
    private int startDate;
    private int endDate;
    private String organizationName;
    private int minimumUsers; 
    private int maximumUsers;
    private String visitType;

    public NewEventEngine(
        String eventName,
        String description,
        String city,
        String address,
        int startDate,
        int endDate,
        String organizationName,
        int minimumUsers,
        int maximumUsers,
        String visitType
    ) {
        this.eventName = eventName;
        this.description = description;
        this.city = city;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizationName = organizationName;
        this.minimumUsers = minimumUsers;
        this.maximumUsers = maximumUsers;
        this.visitType = visitType;
    }

    public String handleRequest()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        ) {
            String query = "INSERT INTO events VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; //
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, eventName);
            statement.setString(2, description);
            statement.setString(3, city); 
            statement.setString(4, address);
            statement.setInt(5, startDate);
            statement.setInt(6, endDate);
            statement.setString(7, organizationName);
            statement.setInt(8, minimumUsers); 
            statement.setInt(9, maximumUsers); 
            statement.setString(10, visitType); 
            statement.setBoolean(11, true);
            if(statement.executeUpdate() == 1)
            {
                return new NewEventReply(true, true).toJSONString();
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return new NewEventReply(true, false).toJSONString();
    }
}