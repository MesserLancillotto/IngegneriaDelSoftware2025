package User;
import java.util.*;

public class Voluntary extends User
{
	private ArrayList <String> allowedVisitType;

	//COSTRUTTORE BASE
	public Voluntary (String userName, String cityOfResidence, int birthYear, String password, String roleTitle, String organization, ArrayList <String> allowedVisitType)
	{
		this.userName = userName;
		this.cityOfResidence = cityOfResidence;
		this.birthYear = birthYear;
        this.password = password;
		this.roleTitle = roleTitle;
		this.organization = organization;
		this.allowedVisitType = new ArrayList<>(allowedVisitType);

		new VoluntaryMenu (userName);
	}

	//COSTRUTTORE PRIMO ACCESSO
	public Voluntary (String userName,String password, String roleTitle)
	{
		this.userName = userName;
        this.password = password;
		this.roleTitle = roleTitle;

		first_access();
		new VoluntaryMenu (userName);
	}
	
	private void first_access()
	{
		while(set_new_password());
	}

	public ArrayList <String> getAllowedVisitTypes ()
	{
		return allowedVisitType;
	}

}
