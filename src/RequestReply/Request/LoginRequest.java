package RequestReply.Request;

public class LoginRequest implements RequestType
{
    public LoginRequest(){}

    public String toJSONString()
    {
        return "\"\":\"\"";
    }
}