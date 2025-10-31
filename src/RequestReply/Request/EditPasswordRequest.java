package RequestReply.Request;

public class EditPasswordRequest implements RequestType 
{

    private String newPassword;

    public EditPasswordRequest
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