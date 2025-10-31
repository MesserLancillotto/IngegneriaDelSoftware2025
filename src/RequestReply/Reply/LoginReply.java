package RequestReply.Reply;

public class LoginReply implements ReplyType
{
    private boolean loginSuccessful;
    private String userName;
    private String cityOfResidence;
    private int birthYear;
    private String role;
    private String organization;

    public LoginReply
    (
        boolean loginSuccessful,
        String userName,
        String cityOfResidence,
        int birthYear,
        String role,
        String organization
    ) {
        this.loginSuccessful = loginSuccessful;
        this.userName = userName;
        this.cityOfResidence = cityOfResidence;
        this.birthYear = birthYear;
        this.role = role;
        this.organization = organization;
    }

    public String toJSONString()
    {
        String template = """
        \t"loginSuccessful":%b,
        \t"userName":"%s",
        \t"cityOfResidence":"%s",
        \t"birthYear":%d,
        \t"role":"%s",
        \t"organization":"%s"
        """;
        return String.format(
            template,
            loginSuccessful,
            userName,
            cityOfResidence,
            birthYear,
            role,
            organization
        );
    }
}