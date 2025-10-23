
package Server.Engine;

import RequestReply.Reply.GetVoluntariesForVisitReply;

class GetVoluntariesForVisitEngine extends LoginEngine
{
    private String userID;
    private String userPassword;

    public GetVoluntariesForVisitEngine
    (
        String userID,
        String userPassword,
        String eventName
    )
    {
        this.userID = userID;
        this.userPassword = userPassword;
        this.eventName = eventName
    }

    String handleRequest() throws SQLException
    {
        if(!canLogIn())
        {
            return new LoginReply(false).toJSONString();
        }
        try
        {
            Connection connection = connectDB(dbUrl, "sa", "");
            String query = "SELECT userID FROM eventsVoluntaries WHERE eventName='%s'";
            query = String.format(query, userID, userPassword);
            try 
            (
                Statement statement = connection.createStatement()
            ) {
                return new LoginReply(statement.execute(query)).toJSONString();
            } catch (SQLException e) {
                return new LoginReply(false).toJSONString();
            }

        }
    }
}
