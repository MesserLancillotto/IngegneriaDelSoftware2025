package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;

class NewOrganizationEngine extends Engine
{
    private String organizationName;
    private ArrayList<String> territoriesOfCompetence;
    
    public NewOrganizationEngine
    (
        String organizationName,
        ArrayList<String> territoriesOfCompetence
    )
    {
        this.organizationName = organizationName;
        this.territoriesOfCompetence = territoriesOfCompetence;
    }

    public boolean organizationAlreadyPresent()
    {
       try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        )
        {
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
        boolean accessSuccesful = false;
        boolean registrationSuccessful = false;
        int territoriesAdded = 0;

        if(!organizationAlreadyPresent())
        {
            try
            (
                Connection connection = connectDB(dbUrl, "sa", "");
            )
            {
                for(String territory : territoriesOfCompetence)
                {
                    String query = "INSERT INTO organizations VALUES ('%s', '%s')";
                    query = String.format(query, organizationName, territory);
                    Statement statement = connection.createStatement();
                    territoriesAdded += statement.executeUpdate(query);
                }
            }catch(Exception e)
            {
                
            }
        }
        return new NewOrganizationReply(
                accessSuccesful,
                registrationSuccessful,
                territoriesAdded
            ).toJSONString();
    }
}
