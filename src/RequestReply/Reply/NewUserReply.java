package RequestReply.Reply;

public class NewUserReply implements ReplyType
{
    private boolean accessSuccesful;
    private String userID;

    public NewUserReply
    (
        boolean accessSuccesful,
        String userID
    ) { 
        this.accessSuccesful = accessSuccesful;;
        this.userID = userID;
    }

    public String toJSONString()
    {
        String template = """{
        "loginSuccessful":"%s"
        "userID":"%s"}
        """;
        return String.format(template, accessSuccesful, userID);
    }
}