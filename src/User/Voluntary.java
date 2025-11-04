package User;
import java.util.*;
import org.json.*;
import Client.Client;

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
		this.allowedVisitType = allowedVisitType;

		new VoluntaryMenu (userName, this.allowedVisitType);
	}

	//COSTRUTTORE PRIMO ACCESSO
	public Voluntary (String userName,String password, String roleTitle)
	{
		this.userName = userName;
        this.password = password;
		this.roleTitle = roleTitle;
		
		if(first_access())
			new VoluntaryMenu (userName, this.allowedVisitType);
		else
			System.out.println ("\nERRORE durantel'esecuzione dell'applicazione");
	}
	
	private boolean first_access()
	{
		while(set_new_password());
		Client.getInstance().get_user_data(userName);
		String getDataResponse = Client.getInstance().make_server_request();
		JSONObject dictionary = new JSONObject(getDataResponse);
		if (dictionary.getBoolean("loginSuccessful"))
		{
			this.birthYear = dictionary.getInt("birthYear");
			this.organization = dictionary.getString("organization");
			this.cityOfResidence = dictionary.getString("cityOfResidence");
			this.allowedVisitType = JSONObjectMethod.jsonArrayConverter(dictionary.getJSONArray("allowedVisitType"));
			return true;
		}
		else
		{
			System.out.println ("\nERRORE! Caricamento dati non riuscito");
			return false;
		}

	}

	public ArrayList <String> getAllowedVisitTypes ()
	{
		return allowedVisitType;
	}

}
