public class ModifyUsersForEventReply implements ReplyType
{
    private boolean loginSuccessful;
    private boolean querySuccesful;

    public ModifyUsersForEventReply
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
        \t"loginSuccessful"=%b,
        \t"querySuccesful"=%b
        }
        """, loginSuccessful, querySuccesful);
    }
}