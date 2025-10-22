package  RequestReply.Request;

import RequestReply.UserRoleTitle.*;

public class NewUserReply implements RequestType
{
    private String userID;

    public LoginRequest
    (
        String userID;
    ) { 
        this.userID = userID;
    }

    public String toJSONString()
    {
        return new StringBuilder("{\n\"userID\":\"")
            .append(userID)
            .append("\"\n}")
            .toString();
    }
}