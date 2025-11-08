package RequestReply.Reply;

import org.json.*;

public class EditAllowedVisitFromVoluntaryReply implements ReplyType 
{
    private boolean accessSuccesful;
    private int removedVisits;
    private int appendedVisits;

    public EditAllowedVisitFromVoluntaryReply
    (
        boolean accessSuccesful,
        int removedVisits,
        int appendedVisits
    ) {
        this.accessSuccesful = accessSuccesful;
        this.removedVisits = removedVisits;
        this.appendedVisits = appendedVisits;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("accessSuccesful", accessSuccesful);
        json.put("removedVisits", removedVisits);
        json.put("appendedVisits", appendedVisits);
        return json.toString();
    }
}