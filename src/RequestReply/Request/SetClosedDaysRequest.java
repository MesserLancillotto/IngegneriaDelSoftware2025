package RequestReply.Request;

import RequestReply.UserRoleTitle.*;

public class SetClosedDaysRequest implements RequestType
{
    private int startDate;
    private int endDate;
    private String organization;

    public SetClosedDaysRequest
    (
        int startDate,
        int endDate,
        String organization
    ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.organization = organization;
    }

    public String toJSONString()
    {
        String template = """
        \t"startDate":%d,
        \t"endDate":%d,
        \t"organization":"%s"
        """;
        return String.format(template, startDate, endDate, organization);
    }
}
