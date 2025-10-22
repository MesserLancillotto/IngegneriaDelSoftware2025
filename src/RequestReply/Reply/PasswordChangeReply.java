package RequestReply.Reply;

public class PasswordChangeReply implements ReplyType 
{
    private boolean passwordChangeSuccessful;

    public PasswordChangeReply
    (
        boolean passwordChangeSuccessful
    ) {
        this.passwordChangeSuccessful = passwordChangeSuccessful;
    }

    public String toJSONString()
    {
        return new StringBuilder("{\n\"passwordChangeSuccessful\":\"")
            .append(passwordChangeSuccessful ? "TRUE" : "FALSE")
            .append("\"\n}")
            .toString();
    }
}