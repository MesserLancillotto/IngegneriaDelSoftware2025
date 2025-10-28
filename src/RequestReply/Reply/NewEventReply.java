package RequestReply.Reply;

public class NewEventReply implements ReplyType
{
    private boolean accessSuccesful;
    private boolean registrationSuccesful;

    public NewEventReply
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
        "loginSuccessful":"%s"
        "registrationSuccesful":"%s"
        }
        """;
        return String.format(template, accessSuccesful, registrationSuccesful);
    }
}