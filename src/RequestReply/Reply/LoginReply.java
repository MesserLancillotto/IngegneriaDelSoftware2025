package  RequestReply.Reply;

import RequestReply.UserRoleTitle.*;

public class LoginReply implements RequestType
{
    private boolean loginSuccessful;

    public LoginReply
    (
        boolean loginSuccessful  
    ) {
        this.loginSuccessful = loginSuccessful;
    }

    public String toJSONString()
    {
        return new StringBuilder("{\n\"loginSuccessful\":\"")
            .append(loginSuccessful ? "TRUE" : "FALSE")
            .append("\"\n}")
            .toString();
    }
}
}