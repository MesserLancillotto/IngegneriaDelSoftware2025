package User;
import java.util.*;

import org.h2.store.Data;
import org.json.JSONObject;

import Client.Client;


public class Configurator extends User
{
    // costruttore per primo accesso del configuratore
    public Configurator (String tmpUserName, String tmpPassword, String roleTitle)
    {
        StringBuilder userId = new StringBuilder();
        userId.append(UserTui.getStringNoTrim("Inserisci il nome: "));
        userId.append (" ");
        userId.append(UserTui.getStringNoTrim("Inserisci il cognome: "));
        
        this.password = UserTui.getPasswordFromUser ("Inserisci la nuova password");
        this.cityOfResidence = UserTui.getString("Inserisci la tua città di residenza");
		this.birthYear = UserTui.getInteger("Inserisci l'anno di nascita", 1900, 2025);
        this.roleTitle = roleTitle;
        
        Client.getInstance().set_new_user(userId.toString(), password, cityOfResidence, birthYear);
        String newUserAnswer = Client.getInstance().make_server_request();
        JSONObject dictionary = new JSONObject(newUserAnswer);

        if (dictionary.getBoolean("loginSuccessful"))
        {
            userName = dictionary.getString("userID");
            Client.getInstance().setUserID(userName);
            Client.getInstance().setUserPassword(password);
            set_basic_app_configuration (); // configurazione base dell'app da fare al primo accesso del configuratore
            new ConfiguratorMenu(true, organization);
		    new ConfiguratorMenu (organization);
        }
        else
        {
            System.out.println ("Errore nella creazione utente!");
        }
    }
 
    //secondo costruttore, per quando i dati vengono recuperati dal server
    public Configurator (String userName, String cityOfResidence, int birthYear, String password, String roleTitle, String organization,ArrayList <String> allowedVisitType)
	{
		this.userName = userName;
		this.cityOfResidence = cityOfResidence;
		this.birthYear = birthYear;
        this.password = password;
		this.roleTitle = roleTitle;
        this.organization = organization;
		
		new ConfiguratorMenu (organization);
	}
	
	// metodo per fissare l'ambito territoriale e il numero max di persone iscrivibili dal fruitore (Vedi punto 3, Versione 1)
	public void set_basic_app_configuration ()
	{
        String organizationName = UserTui.getStringNoTrimWithConfirm("\nCome si chiama l'organizzazione per cui lavori");
		ArrayList <String> areaOfInterest = UserTui.getStringArray("In che zona opera questa associazione (Inserire un luogo alla volta)", 
                                                                        "Vuoi inserire un'altra zona");
        Client.getInstance().set_new_organization(organizationName, areaOfInterest);
        String newOrganizationResponse = Client.getInstance().make_server_request();
        JSONObject dictionary = new JSONObject(newOrganizationResponse);
        UserTui.operationIsSuccessful (dictionary.getBoolean("registrationSuccessful"));
        organization = organizationName;
        
        int maxPeopleForSubscription = UserTui.getInteger("Quante persone un fruitore dell'applicazione può iscrivere con una sola iscrizione", 0, 100);
        JSONObjectCreator.setMaxPeopleForSubscription(maxPeopleForSubscription);
	}
}


