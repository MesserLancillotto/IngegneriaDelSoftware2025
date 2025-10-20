package Client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Place 
{
    HashMap <Visit, Voluntary> visitAssociation = new HashMap <>();
	String placeName;
	
	public Place (String placeName, String voluntaryName, String visitType)
	{
		this.placeName = placeName;
		
	}
	
	public Set <String> get_type_visit_list ()
	{
		Set <String> typeVisitList = new HashSet <>();
		for (Map.Entry <Visit, Voluntary> tmpMap : visitAssociation.entrySet())
		{
			typeVisitList.add(tmpMap.getKey().visitType);
		}
		return typeVisitList;
	}
	
	public Set <String> get_voluntary_name_and_visit_type ()
	{
		return new HashSet<>();	// da implementare
	}    
}
