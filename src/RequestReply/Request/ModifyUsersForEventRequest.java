package RequestReply.Request;

public class ModifyUsersForEventRequest
{
    private String userID;
    private String password;
    private String event;
    private int minimumUsers;
    private int maximumUsers;

    public ModifyUsersForEventRequest
    (
        String userID,
        String password,
        String event,
        int minimumUsers,
        int maximumUsers
    ) {
        this.userID = userID;
        this.password = password;
        this.event = event;
        this.minimumUsers = minimumUsers;
        this.maximumUsers = maximumUsers;
    }

    public String toJSONString()
    {
        String request = """
        \t"userID":"%s",
        \t"password":"%s",
        \t"event":"%s",
        \t"minimumUsers":%d,
        \t"maximumUsers":%d
        """;
        return String.format(request,userID, password, event, minimumUsers, maximumUsers);
    }
}