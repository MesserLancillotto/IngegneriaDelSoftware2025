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
        String template = "\"newPassword\":\"%s\"";
        return String.format(template, newPassword);
    }
}