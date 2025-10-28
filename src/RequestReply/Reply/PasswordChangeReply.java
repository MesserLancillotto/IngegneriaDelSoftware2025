package RequestReply.Reply;

public class PasswordChangeReply implements ReplyType 
{
    private boolean accessSuccesful;
    private boolean passwordChangeSuccessful;

    public PasswordChangeReply
    (
        boolean accessSuccesful,
        boolean passwordChangeSuccessful
    ) {
        this.accessSuccesful = accessSuccesful;
        this.passwordChangeSuccessful = passwordChangeSuccessful;
    }

    public String toJSONString()
    {
        String template = """
        {
        \t"loginSuccessful":"%s"
        \t"passwordChangeSuccessful":"%s"
        }
        """;
        return String.format(
            template, 
            accessSuccesful, 
            passwordChangeSuccessful);
    }
}