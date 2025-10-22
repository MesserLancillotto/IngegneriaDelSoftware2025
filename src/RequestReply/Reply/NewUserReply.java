package RequestReply.Reply;

public class NewUserReply implements ReplyType
{
    private String userID;

    public NewUserReply
    (
        String userID
    ) { 
        this.userID = userID;
    }

    public String toJSONString()
    {
        return new StringBuilder("{\n\"userID\":\"")
            .append(userID)
            .append("\"\n}")
            .toString();
    }
}