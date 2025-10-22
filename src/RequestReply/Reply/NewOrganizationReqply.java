package  RequestReply.Reply;

import RequestReply.UserRoleTitle.*;
import java.util.*;

public class NewOrganizationReply implements ReplyType
{
    private boolean registrationSuccessful;

    public NewOrganizationRequest
    (
        boolean registrationSuccessful  
    ) {
        this.registrationSuccessful = registrationSuccessful;
    }

    public String toJSONString()
    {
        return new StringBuilder("{\n\"registrationSuccessful\":\"")
            .append(registrationSuccessful ? "TRUE" : "FALSE")
            .append("\"\n}")
            .toString();
    }
}