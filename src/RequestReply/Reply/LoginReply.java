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
        return new StringBuilder("{\n\"loginSuccessful\":\"")
            .append(loginSuccessful ? "TRUE" : "FALSE")
            .append("\"\n}")
            .toString();
    }
}