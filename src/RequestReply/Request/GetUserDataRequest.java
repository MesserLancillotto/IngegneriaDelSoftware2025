package RequestReply.Request;

public class GetUserDataRequest implements RequestType
{
    private String target;

    public GetUserDataRequest(String target){
        this.target = target;
    }

    public String toJSONString()
    {
        return String.format("\"target\":\"%s\"", target);
    }
}