package RequestReply.Request;

import java.util.*;
import org.json.*;

public class GetVoluntaryDisponibilityRequest implements RequestType
{    
    private String targetID; 

    public GetVoluntaryDisponibilityRequest(String targetID)
    {
        this.targetID = targetID;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("targetID", targetID);
        return json.toString();
    }
}