package RequestReply.Request;

import java.util.*;

public class NewOrganizationRequest implements RequestType
{
    private String userID;
    private String password;
    private String organizationName;
    private ArrayList<String> territoriesOfCompetence ;

    public NewOrganizationRequest
    (
        String userID,
        String password,
        String organizationName,
        ArrayList<String> territoriesOfCompetence
    ) {
        this.userID = userID;
        this.password = password;
        this.organizationName = organizationName;
        this.territoriesOfCompetence = territoriesOfCompetence;
    }

    public String toJSONString()
    {
        StringBuilder response = new StringBuilder("\"organizationName\":\"").append(organizationName).append("\",\n\"territoriesOfCompetence\":\n[\n");
        for(String territory : territoriesOfCompetence)
        {
            response.append("\t\"").append(territory).append("\",\n");
        }
        response.deleteCharAt(response.length() - 2).append("]");
        return response.toString();
    }
}