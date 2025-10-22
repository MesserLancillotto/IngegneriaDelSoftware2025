package  RequestReply.Request;

import RequestReply.UserRoleTitle.*;

public class GetVoluntariesForVisit implements RequestType
{
    private String organizationName;

    public GetVoluntariesForVisit
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