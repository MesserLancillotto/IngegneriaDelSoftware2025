package RequestReply.Request;

import RequestReply.UserRoleTitle.*;

public class NewUserRequest implements RequestType
{
    private String userName;
    private String newPassword;
    private String cityOfResidence;
    private Integer birthYear;
    private UserRoleTitle role;

    public NewUserRequest
    (
        String userName,
        String newPassword,
        String cityOfResidence,
        Integer birthYear,
        UserRoleTitle role 
    ) {
        this.userName = userName;
        this.newPassword = newPassword;
        this.cityOfResidence = cityOfResidence;
        this.birthYear = birthYear;
        this.role = role;
    }

    public String toJSONString()
    {
        String template = """
        "userName": "%s",
        "newPassword":"%s",
        "cityOfResidence": "%s", 
        "birthYear": "%s",
        "role": "%s"
        """;
        return String.format(
            template, 
            userName,
            newPassword,
            cityOfResidence,
            birthYear,
            role).replace("\"\n", "\"");
    }
}