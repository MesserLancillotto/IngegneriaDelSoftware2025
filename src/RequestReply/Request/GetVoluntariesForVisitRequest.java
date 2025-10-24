package RequestReply.Request;

public class GetVoluntariesForVisitRequest implements RequestType
{
    
    private  String organizationName;
    private  String eventName;
    private  int eventStart;
    private  int eventEnd;

    public GetVoluntariesForVisitRequest
    (
        String organizationName,
        String eventName,
        int eventStart,
        int eventEnd
    ) {
        this.organizationName = organizationName;
        this.eventName = eventName;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    public String toJSONString()
    {
        String template = """
            "organizationName":"%s",
            "eventName":"%s",
            "eventStart":"%s",
            "eventEnd":"%s"
""";
        return String.format(
            template, 
            organizationName, 
            eventName,
            eventStart,
            eventEnd).replace("\"\n", "\"");
    }
}