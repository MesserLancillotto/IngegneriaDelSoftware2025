package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;
import RequestReply.UserRoleTitle.*;
import RequestReply.ComunicationType.*;

public class SetNewEventEngine extends Engine
{
    private String userID;
    private String userPassword;
    private String eventName;
    private String description;
    private String city;
    private String address;
    private int startDate;
    private int endDate;
    private String organization;
    private int minimumUsers; 
    private int maximumUsers;
    private int maximumFriends;
    private String visitType;

    public SetNewEventEngine(
        String userID,
        String userPassword,
        String eventName,
        String description,
        String city,
        String address,
        int startDate,
        int endDate,
        String organization,
        int minimumUsers,
        int maximumUsers,
        int maximumFriends,
        String visitType
    ) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.eventName = eventName;
        this.description = description;
        this.city = city;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organization = organization;
        this.minimumUsers = minimumUsers;
        this.maximumUsers = maximumUsers;
        this.maximumFriends = maximumFriends;
        this.visitType = visitType;
    }

    public String handleRequest()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        ) {
            String roleCheckQuery = "SELECT role, organization FROM users WHERE userName = ? AND userPassword = ?";
            PreparedStatement roleStatement = connection.prepareStatement(roleCheckQuery);
            roleStatement.setString(1, userID);
            roleStatement.setString(2, userPassword);
            ResultSet result = roleStatement.executeQuery();
            if(!result.next() || result.getString("role") != "CONFIGURATOR" || result.getString("organization") != this.organization)
            {
                return new SetNewEventReply(false, false, false).toJSONString(); 
            }

            String checkClosureDaysQuery = "SELECT DISTINCT organizationName FROM closedDays WHERE (startDay BETWEEN ? AND ?) OR (endDay BETWEEN ? AND ?)";
            PreparedStatement checkClosureDaysStatement = connection.prepareStatement(checkClosureDaysQuery);
            checkClosureDaysStatement.setInt(1, startDate);
            checkClosureDaysStatement.setInt(2, endDate);
            checkClosureDaysStatement.setInt(3, startDate);
            checkClosureDaysStatement.setInt(4, endDate);
            ResultSet checkClosureDaysResult = checkClosureDaysStatement.executeQuery();
            if(checkClosureDaysResult.next())
            {
                return new SetNewEventReply(true, false, true).toJSONString();
            } 

            String query = "INSERT INTO events VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, eventName);
            statement.setString(2, description);
            statement.setString(3, city); 
            statement.setString(4, address);
            statement.setInt(5, startDate);
            statement.setInt(6, endDate);
            statement.setString(7, organization);
            statement.setInt(8, minimumUsers); 
            statement.setInt(9, maximumUsers);
            statement.setInt(10, maximumFriends);
            statement.setString(11, visitType); 
            statement.setBoolean(12, true);
            if(statement.executeUpdate() == 1)
            {
                return new SetNewEventReply(true, true, false).toJSONString();
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return new SetNewEventReply(true, false, false).toJSONString();
    }
}