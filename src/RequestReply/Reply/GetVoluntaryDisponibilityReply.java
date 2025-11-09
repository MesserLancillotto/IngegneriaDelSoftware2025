package RequestReply.Reply;

import org.json.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class GetVoluntaryDisponibilityReply implements ReplyType
{
    private ArrayList<Map.Entry<String, Integer>> events;

    public GetVoluntaryDisponibilityReply(ArrayList<Map.Entry<String, Integer>> events)
    {
        this.events = events;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        JSONArray eventsArray = new JSONArray();
        
        for (Map.Entry<String, Integer> event : events) {
            JSONObject eventObj = new JSONObject();
            eventObj.put("event", event.getKey());
            eventObj.put("time", event.getValue());
            eventsArray.put(eventObj);
        }
        
        json.put("events", eventsArray);
        return json.toString();
    }
}