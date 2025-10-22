package RequestReply.Reply;

import java.util.*;

public class NewOrganizationReply implements ReplyType
{
    private boolean registrationSuccessful;

    public NewOrganizationReply
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