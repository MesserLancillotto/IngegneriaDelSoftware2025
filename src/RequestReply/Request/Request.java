package RequestReply.Request;

import RequestReply.UserRoleTitle.*;

public class Request 
{
    CommunicationType requestType;
    String userID;
    String userPassword;
    String requestBody;

    public Request
    (
        CommunicationType requestType,
        String userID,
        String userPassword,
        RequestType request
    ) {
        this.requestType = requestType;
        this.userID = userID;
        this.userPassword = userPassword;
        this.requestBody = request.toJSONString();
    }
}
