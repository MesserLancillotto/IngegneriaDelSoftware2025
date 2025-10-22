package RequestReply.Request;

public class PasswordChangeRequest implements RequestType 
{

    private String newPassword;

    public PasswordChangeRequest
    (
        String newPassword
    ) {
        this.newPassword = newPassword;
    }

    public String toJSONString()
    {
        return new StringBuilder("{\n\"newPassword\":\"").append(this.newPassword).append("\"\n}").toString();
    }
}