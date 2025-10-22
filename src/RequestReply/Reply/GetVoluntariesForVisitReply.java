package RequestReply.Reply;

import java.util.*;

public class GetVoluntariesForVisitReply implements ReplyType
{
    private String event;
    private ArrayList<String> voluntaries;

    public GetVoluntariesForVisitReply
    (
        String event,
        ArrayList<String> voluntaries
    ) {
        this.event = event;
        this.voluntaries = voluntaries;
    }

    public String toJSONString()
    {
        StringBuilder reply = new StringBuilder("{\n\"event\":\"").append(event).append("\"\n[");

        for(String voluntary : voluntaries)
        {
            reply.append("\"").append(voluntary).append("\",\n");
        }
        reply.append("\n]\n}");
        return reply.toString();
    }
}