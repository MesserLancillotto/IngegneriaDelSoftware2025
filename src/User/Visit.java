package User;
import java.util.*;

public class Visit 
{
	private enum stateOfVisit {PROPOSTA, COMPLETA, CONFERMATA, CANCELLATA, EFFETTUATA};	

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
	}

	public String getVisitType ()
	{
		return visitType;
	}
}
