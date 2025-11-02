package RequestReply.Reply;

import java.util.*;
import org.json.*;

public class GetVoluntariesForVisitReply
{
    private boolean loginSuccessful;
    private ArrayList<String> userIDs;
    
    public GetVoluntariesForVisitReply(boolean loginSuccessful, ArrayList<String> userIDs) {
        this.loginSuccessful = loginSuccessful;
        this.userIDs = userIDs;
    }
    
    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("loginSuccessful", loginSuccessful);
        
        JSONArray usersArray = new JSONArray();
        for (String userID : userIDs) {
            usersArray.put(userID);
        }
        json.put("userIDs", usersArray);
        
        return json.toString();
    }
}