package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import RequestReply.UserRoleTitle.*;
import java.util.*;

public class NewOrganizationEngine extends Engine
{
    private String userID;
    private String userPassword;
    private String organizationName;
    private ArrayList<String> territoriesOfCompetence;
    
    private int territoriesAdded;

    public NewOrganizationEngine
    (
        String userID,
        String userPassword,
        String organizationName,
        ArrayList<String> territoriesOfCompetence
    )
    {
        this.userID = userID;
        this.userPassword = userPassword;
        this.organizationName = organizationName;
        this.territoriesOfCompetence = territoriesOfCompetence;
        this.territoriesAdded = 0;
    }

    public String handleRequest() 
    {
       try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        )
        {
            String checkPermitsQuery = "SELECT role FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement checkPermitsStatement = connection.prepareStatement(checkPermitsQuery);
            checkPermitsStatement.setString(1, userID);
            checkPermitsStatement.setString(2, userPassword);
            ResultSet checkPermitsResult = checkPermitsStatement.executeQuery();
            if(
                !checkPermitsResult.next() 
                || !checkPermitsResult.getString("role").equals("CONFIGURATOR")
            ) {
                return new NewOrganizationReply(false, false, 0).toJSONString();
            }

            String checkAlreadyPresentOrganizationQuery = "SELECT DISTINCT organizationName FROM organizations WHERE organizationName = ?";
            PreparedStatement checkAlreadyPresentOrganizationStatement = connection.prepareStatement(checkAlreadyPresentOrganizationQuery);
            checkAlreadyPresentOrganizationStatement.setString(1, organizationName);
            ResultSet checkAlreadyPresentOrganizationResult = checkAlreadyPresentOrganizationStatement.executeQuery();
            if(checkAlreadyPresentOrganizationResult.next())
            {
                return new NewOrganizationReply(true, false, 0).toJSONString(); 
            }
            
            for(String territory : territoriesOfCompetence)
            {
                String query = "INSERT INTO organizations VALUES ( ?, ? )";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, organizationName);
                statement.setString(2, territory);
                int v = statement.executeUpdate();
                this.territoriesAdded += v; 
            }
            return new NewOrganizationReply(
                    true,
                    true,
                    this.territoriesAdded
                ).toJSONString();
        } catch(Exception e)
        {
            e.printStackTrace();
            return new NewOrganizationReply(false, false, 0).toJSONString(); 
        }
    }
}