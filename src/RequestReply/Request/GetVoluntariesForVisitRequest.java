package  RequestReply.Request;

import RequestReply.UserRoleTitle.*;

public class GetVoluntariesForVisitRequest implements RequestType
{
    private String organizationName;

    public public class GetVoluntariesForVisitRequest implements RequestType

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