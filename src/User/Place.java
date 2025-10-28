import java.util.*;

public class Place 
{
    HashMap <Visit, Voluntary> visitAssociation = new HashMap <>();
	String placeName;
	
	public Place (String placeName, String visitType, String voluntaryName)
	{
		this.placeName = placeName;
		//visitAssociation.put(null, null)
	}
	
	public Set <String> get_type_visit_list ()
	{
		Set <String> typeVisitList = new HashSet <>();
		for (Map.Entry <Visit, Voluntary> tmpMap : visitAssociation.entrySet())
		{
			typeVisitList.add(tmpMap.getKey().getVisitType());
		}
		return typeVisitList;
	}
	   
}
