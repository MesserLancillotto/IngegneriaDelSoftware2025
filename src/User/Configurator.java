package User;
import java.util.*;

import org.json.JSONObject;
import Client.Client;


public class Configurator extends User
{
    private static final String GET_DATA_NAME = "Inserisci il nome: ";
    private static final String GET_DATA_SURNAME = "Inserisci il cognome: ";
    private static final String GET_DATA_PASSWORD = "Inserisci la nuova password: ";
    private static final String GET_DATA_CITY = "Inserisci la tua città di residenza";
    private static final String GET_DATA_BIRTH_YEAR = "Inserisci l'anno di nascita";
    private static final String GET_DATA_ORGANIZATION = "Inserire il nome dell'organizzazione per cui lavori";
    private static final String GET_DATA_ORGANIZATION_ZONES = "In che zona opera questa associazione (Inserire un luogo alla volta)";
    private static final String GET_DATA_ORGANIZATION_ZONES_LOOP_CONF = "Vuoi inserire un'altra zona";
    private static final String GET_DATA_MAX_SUBCRIPTION = "Quante persone un fruitore dell'applicazione può iscrivere con una sola iscrizione";
    private static final String BASIC_CONF_MSG = "Configurazione base dell'applicazione";
    private static final String ERROR_CREATING_ACCOUNT = "\nErrore nella creazione dell'account, riprova più tardi!\n";
    private static final String ERROR_CONNECTION_SERVER = "\nErrore di connessione col server, riprova più tardi!\n";
    // costruttore per primo accesso del configuratore
    public Configurator (String tmpUserName, String tmpPassword, String roleTitle)
    {
        StringBuilder userId = new StringBuilder();
        userId.append(UserTui.getStringNoTrim(GET_DATA_NAME));
        userId.append (" ");
        userId.append(UserTui.getStringNoTrim(GET_DATA_SURNAME));
        
        this.password = UserTui.getPasswordFromUser (GET_DATA_PASSWORD);
        this.cityOfResidence = UserTui.getString(GET_DATA_CITY);
		this.birthYear = UserTui.getInteger(GET_DATA_BIRTH_YEAR, 1900, 2025);
        this.roleTitle = roleTitle;
        
        Client.getInstance().set_new_user(userId.toString(), password, cityOfResidence, birthYear);
        String newUserAnswer = Client.getInstance().make_server_request();

        if (newUserAnswer.trim().isEmpty() || JSONObjectMethod.isValidJSONObject(newUserAnswer))
        {
            JSONObject dictionary = new JSONObject(newUserAnswer);

            if (dictionary.getBoolean("loginSuccessful"))
            {
                userName = dictionary.getString("userID");
                Client.getInstance().setUserID(userName);
                Client.getInstance().setUserPassword(password);
                if (set_basic_app_configuration ()) // configurazione base dell'app da fare al primo accesso del configuratore
                {
                    new ConfiguratorMenu(true, organization);   // chiamo il metodo in MenuConf -> MenuConfPlace per settare i primi posti
                    new ConfiguratorMenu (organization);
                }
            }
            else
            {
                System.out.println (ERROR_CREATING_ACCOUNT);
            }
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
	public boolean set_basic_app_configuration ()
	{
        UserTui.stampTitle (BASIC_CONF_MSG);
        String organizationName = UserTui.getStringNoTrimWithConfirm(GET_DATA_ORGANIZATION);
		ArrayList <String> areaOfInterest = UserTui.getStringArray(GET_DATA_ORGANIZATION_ZONES, GET_DATA_ORGANIZATION_ZONES_LOOP_CONF);
        Client.getInstance().set_new_organization(organizationName, areaOfInterest);
        String newOrganizationResponse = Client.getInstance().make_server_request();
        if (newOrganizationResponse.trim().isEmpty() || JSONObjectMethod.isValidJSONObject(newOrganizationResponse))
        {
            JSONObject dictionary = new JSONObject(newOrganizationResponse);
            this.organization = organizationName;
            if (!UserTui.operationIsSuccessful (dictionary.getBoolean("registrationSuccessful")))
                return false;
        }
        else
        {
            System.out.println (ERROR_CONNECTION_SERVER);
            return false;
        }
        
        int maxPeopleForSubscription = UserTui.getInteger(GET_DATA_MAX_SUBCRIPTION, 0, 100);
        JSONObjectCreator.setMaxPeopleForSubscription(maxPeopleForSubscription);

        return true;    // se tutto è andato a buon fine allora si continua con l'applicazione
	}
}


