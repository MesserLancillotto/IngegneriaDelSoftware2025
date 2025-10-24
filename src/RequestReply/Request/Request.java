package RequestReply.Request;

import RequestReply.ComunicationType.*;

public class Request 
{
    private ComunicationType requestType;
    private String userID;
    private String userPassword;
    private String requestBody;

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

    public String toJSONString()
    {
        String template = "{\n\"requestType\":\"%s\"\n\"userID\":\"%s\"\"userPassword\":\"%s\"\n\"requestBody\":\"%s\"\n}";
        return String.format(template, requestType.name(), userID, userPassword, requestBody);
    }
}

