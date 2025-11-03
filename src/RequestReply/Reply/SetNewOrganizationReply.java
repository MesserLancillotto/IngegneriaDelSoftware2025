package RequestReply.Reply;

import java.util.*;

public class SetNewOrganizationReply implements ReplyType
{
    private boolean accessSuccesful;
    private boolean registrationSuccessful;
    private int territoriesAdded;

    public SetNewOrganizationReply
    (
        boolean accessSuccesful,
        boolean registrationSuccessful,
        Integer territoriesAdded  
    ) {
        this.accessSuccesful = accessSuccesful;
        this.registrationSuccessful = registrationSuccessful;
        this.territoriesAdded = territoriesAdded;
    }

    public String toJSONString()
    {
        String template = """
        {
        \t"loginSuccessful":"%s",
        \t"registrationSuccessful":"%s",
        \t"territoriesAdded":"%s"
        }""";
        return String.format(
            template,
            accessSuccesful,
            registrationSuccessful,
            territoriesAdded);
    }
}