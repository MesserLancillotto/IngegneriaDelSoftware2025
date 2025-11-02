package RequestReply.Reply;

import org.json.*;

public class SetClosedDaysReply implements ReplyType
{
    private boolean loginSuccessful;
    private boolean querySuccesful;
    private boolean closed;

    public SetClosedDaysReply
    (
        boolean loginSuccessful,
        boolean querySuccesful,
        boolean closed
    ) {
        this.loginSuccessful = loginSuccessful;
        this.querySuccesful = querySuccesful;
        this.closed = closed;
    }

    public String toJSONString()
    {
        return String.format("""
        {
        \t"loginSuccessful":%b,
        \t"querySuccesful":%b
        \t"closed":%b
        }
        """, loginSuccessful, querySuccesful, closed);
    }
}
