package RequestReply.Request;

public class GetVoluntariesForVisitRequest implements RequestType
{
    
    private  String eventName;

    public GetVoluntariesForVisitRequest
    (
        String eventName
    ) {
        this.eventName = eventName;
    }

    public String toJSONString()
    {
        String template = """
            "eventName":"%s",
        """;
        return String.format(
            template, 
            eventName
        );
    }
}