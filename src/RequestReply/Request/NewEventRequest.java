package RequestReply.Request;

import java.util.*;

public class NewEventRequest implements RequestType
{
    private String eventName;
    private String description;
    private String city;
    private String address;
    private int startDate;
    private int endDate;
    private String organizationName;
    private int minimumUsers; 
    private int maximumUsers;
    private String visitType;

    public NewEventRequest(
        String eventName,
        String description,
        String city,
        String address,
        int startDate,
        int endDate,
        String organizationName,
        int minimumUsers,
        int maximumUsers,
        String visitType
    ) {
        this.eventName = eventName;
        this.description = description;
        this.city = city;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizationName = organizationName;
        this.minimumUsers = minimumUsers;
        this.maximumUsers = maximumUsers;
        this.visitType = visitType;
    }

    public String toJSONString()
    {
        String template = """
            "eventName":"%s",
            "description":"%s",
            "city":"%s",
            "address":"%s",
            "startDate":%d,
            "endDate":%d,
            "organizationName":"%s",
            "minimumUsers":%d,
            "maximumUsers":%d,
            "visitType":"%s"
        """;
        template = String.format(
            template,
            eventName,
            description,
            city,
            address,
            startDate,
            endDate,
            organizationName,
            minimumUsers,
            maximumUsers,
            visitType
        );
        return template;
    }
}