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
    private String address;
    private int startDate;
    private int endDate;
    private String organizationName;

    public NewEventEngine(
        String eventName,
        String description,
        String address,
        int startDate,
        int endDate,
        String organizationName
    ) {
        this.eventName = eventName;
        this.description = description;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizationName = organizationName;
    }

    public String handleRequest()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        ) {
            String query = "INSERT INTO events VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, eventName);
            statement.setString(2, description);
            statement.setString(3, address);
            statement.setInt(4, startDate);
            statement.setInt(5, endDate);
            statement.setString(6, organizationName);
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