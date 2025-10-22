package RequestReply.Request;

import RequestReply.ComunicationType.*;

public class Request 
{
    ComunicationType requestType;
    String userID;
    String userPassword;
    String requestBody;

    public Request
    (
        ComunicationType requestType,
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
