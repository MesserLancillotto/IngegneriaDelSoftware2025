package  RequestReply.Reqply;

import java.util.*;

public class GetVoluntariesForVisit implements RequestType
{
    private String event;
    private ArrayList<String> voluntaries;

    public GetVoluntariesForVisit
    (
        String event;
        ArrayList<String> voluntaries; 
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