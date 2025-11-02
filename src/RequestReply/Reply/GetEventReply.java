package RequestReply.Reply;

import org.json.*;

public class GetEventReply implements ReplyType
{
    private JSONArray result = new JSONArray();

    public void insertEvent(
        String eventName,
        String description,
        String city,
        String address,
        int startDate,
        int endDate,
        String organizationName,
        int minimumUsers,
        int maximumUsers,
        int maximumFriends,
        String visitType,
        boolean confirmed
    ) {
            JSONObject event = new JSONObject();
            event.put("eventName", eventName);
            event.put("description", description);
            event.put("city", city);
            event.put("address", address);
            event.put("startDate", startDate);
            event.put("endDate", endDate);
            event.put("organizationName", organizationName);
            event.put("minUsers", minimumUsers);
            event.put("maxUsers", maximumUsers);
            event.put("maxFriends", maximumFriends);
            event.put("visitType", visitType);
            event.put("confirmed", true);
            result.put(event);
    }

    public String toJSONString()
    {
        return result.toString();
    }
}