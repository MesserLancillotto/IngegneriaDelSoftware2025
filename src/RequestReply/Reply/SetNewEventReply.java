package RequestReply.Reply;

public class SetNewEventReply implements ReplyType
{
    private boolean accessSuccesful;
    private boolean registrationSuccesful;

    public SetNewEventReply
    (
        boolean accessSuccesful,
        boolean registrationSuccesful
    ) { 
        this.accessSuccesful = accessSuccesful;;
        this.registrationSuccesful = registrationSuccesful;
    }

    public String toJSONString()
    {
        String template = """
        {
        \t"loginSuccessful":"%s"
        \t"registrationSuccesful":"%s"
        }
        """;
        return String.format(template, accessSuccesful, registrationSuccesful);
    }
}