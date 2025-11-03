package RequestReply.Reply;

import org.json.*;

public class SetClosedDaysReply implements ReplyType
{
    private boolean loginSuccessful;
    private boolean querySuccesful;

    public SetClosedDaysReply
    (
        boolean loginSuccessful,
        boolean querySuccesful
    ) {
        this.loginSuccessful = loginSuccessful;
        this.querySuccesful = querySuccesful;
    }

    public String toJSONString()
    {
        return String.format("""
        {
        \t"loginSuccessful":%b,
        \t"querySuccesful":%b
        }
        """, loginSuccessful, querySuccesful);
    }
}
