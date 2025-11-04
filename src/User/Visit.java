package User;
import java.util.*;

public class Visit 
{
	StateOfVisit state;

	String eventName;
	String description;
	String city;
	String address;
	int startDate;
	int endDate;
	String organizationName;
	int minUsers;
	int maxUsers;
	int maxFriends;
	String visitType;

	
	public Visit(String eventName, String description, String city, String address, 
             int startDate, int endDate, String organizationName, int minUsers, 
             int maxUsers, int maxFriends, String visitType) 
	{
		this.eventName = eventName;
		this.description = description;
		this.city = city;
		this.address = address;
		this.startDate = startDate;
		this.endDate = endDate;
		this.organizationName = organizationName;
		this.minUsers = minUsers;
		this.maxUsers = maxUsers;
		this.maxFriends = maxFriends;
		this.visitType = visitType;
		
		//DA IMPLEMENTARE
		this.state = StateOfVisit.CONFERMATA;
	}

	public Visit (String eventName, String city, String address, String state, int startDate)
	{
		this.eventName = eventName;
		this.city = city;
		this.address = address;
		this.state = StateOfVisit.fromString(state);
		this.startDate = startDate;
	}

	public String getPlace ()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(city);
		sb.append (":");
		sb.append(address);
		return sb.toString();
	}

	public String getVisitType ()
	{
		return visitType;
	}

	public String getEventName ()
	{
		return eventName;
	}
	public StateOfVisit getVisitState ()
	{
		return state;
	}

	public String getStartDay ()
	{
		return DataManager.fromUnixToNormal (startDate);
	}
}
