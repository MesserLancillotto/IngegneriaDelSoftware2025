package  RequestReply.Request;

import RequestReply.UserRoleTitle.*;
import java.util.*;

public class NewOrganizationRequest implements RequestType
{
    private String organizationName;
    private ArrayList<String> territoriesOfCompetence ;

    public NewOrganizationRequest
    (
        String organizationName,
        ArrayList<String> territoriesOfCompetence
    ) {
        this.organizationName = organizationName;
        this.territoriesOfCompetence = territoriesOfCompetence;
    }

    public String toJSONString()
    {
        StringBuilder response = new StringBuilder("{\n\"organizationName\":\"").append(organizationName).append("\"\n\"territoriesOfCompetence\":\n[");
        for(String territory : territoriesOfCompetence)
        {
            response.append("\"").append(territory).append("\",\n");
        }
        response.append("]\n}");
        return response.toString();
    }
}