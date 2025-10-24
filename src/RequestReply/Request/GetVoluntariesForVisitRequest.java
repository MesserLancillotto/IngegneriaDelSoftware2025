package RequestReply.Request;

public class GetVoluntariesForVisitRequest implements RequestType
{
    private String organizationName;

    GetVoluntariesForVisitRequest
    (
        String organizationName
    ) {
        this.organizationName = organizationName;
    }

    public String toJSONString()
    {
        String template = "\"organizationName\":\"%s\"";
        return String.format(template, organizationName);
    }
}