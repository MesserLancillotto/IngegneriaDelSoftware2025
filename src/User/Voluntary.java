package User;
import java.util.*;

public class Voluntary extends User
{
	public Voluntary (String userName, String cityOfResidence, int birthYear, String password, String roleTitle)
	{
		this.userName = userName;
		this.cityOfResidence = cityOfResidence;
		this.birthYear = birthYear;
        this.password = password;
		this.roleTitle = roleTitle;

		first_access();
		new VoluntaryMenu (userName);
	}
	
	private void first_access()
	{
		while(set_new_password());
	}

}
