package RequestReply.Reply;

public class EditEventReply implements ReplyType
{
    private boolean accessSuccesful;
    private boolean editSuccesful;

    public EditEventReply
    (
        boolean accessSuccesful,
        boolean editSuccesful
    ) { 
        this.accessSuccesful = accessSuccesful;
        this.editSuccesful = editSuccesful;
    }

    public String toJSONString()
    {
        String template = """
        {
        \t"accessSuccesful":%b,
        \t"editSuccesful":%b
        }
        """;
        return String.format(template, accessSuccesful, editSuccesful);
    }
}