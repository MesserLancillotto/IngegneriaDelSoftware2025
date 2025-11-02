package RequestReply.Request;

import java.util.*;
import org.json.*;

public class GetEventRequest implements RequestType
{
    private Map<String, Object> filters;
    
    public GetEventRequest(Map<String, Object> filters) {
        this.filters = filters;
    }
    
    public GetEventRequest() {
        this.filters = new HashMap<>();
    }
    
    public GetEventRequest withCity(String city) {
        filters.put("city", city);
        return this;
    }
    
    public GetEventRequest withOrganization(String organization) {
        filters.put("organizationName", organization);
        return this;
    }
    
    public GetEventRequest withVisitType(String visitType) {
        filters.put("visitType", visitType);
        return this;
    }
    
    public GetEventRequest withConfirmed(boolean confirmed) {
        filters.put("confirmed", confirmed);
        return this;
    }
    
    public GetEventRequest withEventName(String eventName) {
        filters.put("eventName", eventName);
        return this;
    }
    
    public GetEventRequest withAddress(String address) {
        filters.put("address", address);
        return this;
    }
    
    public GetEventRequest withStartDate(int startDate) {
        filters.put("startDate", startDate);
        return this;
    }
    
    public GetEventRequest withEndDate(int endDate) {
        filters.put("endDate", endDate);
        return this;
    }
    
    public GetEventRequest withOrganizationName(String organizationName) {
        filters.put("organizationName", organizationName);
        return this;
    }
    
    public GetEventRequest withMinUsers(int minUsers) {
        filters.put("minUsers", minUsers);
        return this;
    }
    
    public GetEventRequest withMaxUsers(int maxUsers) {
        filters.put("maxUsers", maxUsers);
        return this;
    }
    
    public GetEventRequest withMaxFriends(int maxFriends) {
        filters.put("maxFriends", maxFriends);
        return this;
    }
    
    public String toJSONString() {
        JSONObject json = new JSONObject();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toString();
    }
}