package RequestReply.Request;

import org.json.JSONObject;
import java.util.*;

public class GetEventWithDisponibilityRequest
{
    JSONObject json = new JSONObject();

    public String toJSONString() {
        JSONObject json = new JSONObject();
        return json.toString();
    }
}