package RequestReply.Request;

import java.util.*;

public class NewEventRequest implements RequestType
{
    private String eventName;
    private String description;
    private String address;
    private int startDate;
    private int endDate;
    private String organizationName;

    public NewEventRequest(
        String eventName,
        String description,
        String address,
        int startDate,
        int endDate,
        String organizationName
    ) {
        this.eventName = eventName;
        this.description = description;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizationName = organizationName;
    }

    public String toJSONString()
    {
        String template = """
            "eventName":"%s",
            "description":"%s",
            "address":"%s",
            "startDate":%d,
            "endDate":%d,
            "organizationName":"%s"
        """;
        template = String.format(
            template,
            eventName,
            description,
            address,
            startDate,
            endDate,
            organizationName
        );
        return template;
    }
}