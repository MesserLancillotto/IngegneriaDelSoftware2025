package RequestReply.Reply;

import java.util.*;

public class NewOrganizationReply implements ReplyType
{
    private boolean accessSuccesful;
    private boolean registrationSuccessful;

    public NewOrganizationReply
    (
        boolean accessSuccesful,
        boolean registrationSuccessful  
    ) {
        this.accessSuccesful = accessSuccesful;
        this.registrationSuccessful = registrationSuccessful;
    }

    public String toJSONString()
    {
        String template = """{
        "loginSuccessful":"%s",
        "registrationSuccessful":"%s"
        }""";
        return String.format(
            template,
            accessSuccesful,
            registrationSuccessful);
    }
}