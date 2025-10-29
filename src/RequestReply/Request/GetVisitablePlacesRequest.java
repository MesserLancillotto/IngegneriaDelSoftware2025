package RequestReply.Request;

public class GetVisitablePlacesRequest implements RequestType
{
    private String byCity;
    private String byAddress; 
    
    public GetVisitablePlacesRequest
    (
        String byCity,
        String byAddress
    ) {
        this.byCity = byCity;
        this.byAddress = byAddress;
    }

    public String toJSONString() 
    {
        String template = """
        \t"byCity":"%s",
        \t"byAddress":"%s"
        """;
        return String.format(template, byCity, byAddress);
    }
}