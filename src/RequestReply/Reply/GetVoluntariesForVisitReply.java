package RequestReply.Reply;

import java.util.*;

public class GetVoluntariesForVisitReply implements ReplyType
{
    private boolean accessSuccesful;
    private String event;
    private ArrayList<String> voluntaries;

    public GetVoluntariesForVisitReply
    (
        boolean accessSuccesful,
        String event,
        ArrayList<String> voluntaries
    ) {
        this.accessSuccesful = accessSuccesful;
        this.event = event;
        this.voluntaries = voluntaries;
    }

    public String toJSONString()
    {
        StringBuilder reply = new StringBuilder("{\"loginSuccessful\":\"}")
            .append(accessSuccesful)
            .append("\",\n\"event\":\"")
            .append(event)
            .append("\"\n\"userIDs\":[");
        for(String voluntary : voluntaries)
        {
            reply.append("\"").append(voluntary).append("\",\n");
        }
        reply.append("\n]");
        return reply.toString();
    }
}