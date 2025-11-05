package RequestReply.Request;

import java.util.*;
import org.json.*;

public class SetNewEventRequest implements RequestType
{
    private String eventName;
    private String description;
    private String city;
    private String address;
    private String meetingPoint;
    private int startDate;
    private int endDate;
    private String organization;
    private int minimumUsers; 
    private int maximumUsers;
    private int maximumFriends;
    private String visitType;

    private ArrayList<Strin> visitDays;
    private int startHour;
    private int duration;

    public SetNewEventRequest(
        String eventName,
        String description,
        String city,
        String address,
        String meetingPoint,
        int startDate,
        int endDate,
        String organization,
        int minimumUsers,
        int maximumUsers,
        int maximumFriends,
        String visitType,

        ArrayList<Strin> visitDays,
        ArrayList<Integer> startHour,
        ArrayList<Integer> duration
    ) {
        this.eventName = eventName;
        this.description = description;
        this.city = city;
        this.address = address;
        this.meetingPoint = meetingPoint;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organization = organization;
        this.minimumUsers = minimumUsers;
        this.maximumUsers = maximumUsers;
        this.maximumFriends = maximumFriends;
        this.visitType = visitType;

        this.visitDays = visitDays;
        this.startHour = startHour;
        this.duration = duration;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("eventName", eventName);
        json.put("description", description);
        json.put("city", city);
        json.put("address", address);
        json.put("meetingPoint", meetingPoint);
        json.put("startDate", startDate);
        json.put("endDate", endDate);
        json.put("organization", organization);
        json.put("minimumUsers", minimumUsers);
        json.put("maximumUsers", maximumUsers);
        json.put("maximumFriends", maximumFriends);
        json.put("visitType", visitType);

        json.put("visitDays", visitDays);
        json.put("startHour", startHour);
        json.put("duration", duration);
        return json.toString(); 
    }
}