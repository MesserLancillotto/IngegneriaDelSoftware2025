package RequestReply.Request;

import RequestReply.UserRoleTitle.*;

public class NewUserRequest implements RequestType
{
    private String userName;
    private String cityOfResidence;
    private Integer birthYear;
    private UserRoleTitle role;

    public NewUserRequest
    (
        String userName,
        String cityOfResidence,
        Integer birthYear,
        UserRoleTitle role 
    ) {
        this.userName = userName;
        this.cityOfResidence = cityOfResidence;
        this.birthYear = birthYear;
        this.role = role;
    }

    public String toJSONString()
    {
        String template = """
        "userName": "%s",
        "cityOfResidence": "%s", 
        "birthYear": "%s",
        "role": "%s"
        """;
        return String.format(
            template, 
            userName,
            cityOfResidence,
            birthYear,
            role);
    }
}