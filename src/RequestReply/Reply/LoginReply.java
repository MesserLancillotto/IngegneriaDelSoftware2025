package RequestReply.Reply;

public class LoginReply implements ReplyType
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
        return new StringBuilder("{\"loginSuccessful\":\"")
            .append(loginSuccessful ? "TRUE" : "FALSE")
            .append("\"}")
            .toString();
    }
}