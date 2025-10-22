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
        return new StringBuilder("{\n\"organizationName\":\"")
            .append(organizationName)
            .append("\"\n}")
            .toString();
    }
}