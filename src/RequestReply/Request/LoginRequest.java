package  RequestReply.Request;

import RequestReply.UserRoleTitle.*;

public class LoginRequest implements RequestType
{
    public LoginRequest(){}

    public String toJSONString()
    {
        return "{}";
    }
}