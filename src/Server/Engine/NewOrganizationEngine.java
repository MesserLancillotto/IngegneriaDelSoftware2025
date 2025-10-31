package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
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

    public boolean organizationAlreadyPresent()
    {
       try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        )
        {
            // AGGIUNGI
            String query = "SELECT * FROM organizations WHERE organizationName='%s'";
            query = String.format(query, organizationName);
            Statement statement = connection.createStatement();
            if(statement.executeQuery(query).next())
            {
                return true;
            }
        } catch(Exception e)
        {
            return false;
        }
        return false;
    }

    public String handleRequest()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        )
        {
            String roleCheckQuery = "SELECT role FROM users WHERE userName = ? AND userPassword = ?";
            PreparedStatement roleStatement = connection.prepareStatement(roleCheckQuery);
            roleStatement.setString(1, userID);
            roleStatement.setString(2, userPassword);
            ResultSet result = roleStatement.executeQuery();
            if(!result.next() || result.getString("role") != "CONFIGURATOR")
            {
                return new NewOrganizationReply(false, false, 0).toJSONString(); 
            }
            String organizationAlreadyPresentQuery = "SELECT DISTINCT organizationName FROM organizations WHERE organizationName = ?";
            PreparedStatement organizationStatement = connection.prepareStatement(organizationAlreadyPresentQuery);
            organizationStatement.setString(1, organizationName);
            ResultSet resultOrganizations = organizationStatement.executeQuery();
            if(resultOrganizations.next())
            {
                return new NewOrganizationReply(true, false, 0).toJSONString(); 
            }
            for(String territory : territoriesOfCompetence)
            {
                String query = "INSERT INTO organizations VALUES ('%s', '%s')";
                query = String.format(query, organizationName, territory);
                System.out.println(query);         
                Statement statement = connection.createStatement();
                this.territoriesAdded += statement.executeUpdate(query);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return new NewOrganizationReply(
                true,
                true,
                this.territoriesAdded
            ).toJSONString();
    }
}
