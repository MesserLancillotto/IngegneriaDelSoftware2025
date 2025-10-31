package Server.Engine;

public class ModifyUsersForEventEngine
{
    private String userID;
    private String password;
    private String event;
    private int minimumUsers;
    private int maximumUsers;

    public ModifyUsersForEventEngine
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
}