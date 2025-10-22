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
        return new StringBuilder("{\n")
            .append("\"userName\":\"")
            .append(userName)
            .append("\"\n")
            .append("\"cityOfResidence\":\"")
            .append(cityOfResidence)
            .append("\"\n")
            .append("\"birthYear\":\"")
            .append(birthYear.toString())
            .append("\"\n")
            .append("\"role\":\"")
            .append(UserRoleTitleStringConverter.roleToString(role))
            .append("\"\n}")
            .toString();
    }
}