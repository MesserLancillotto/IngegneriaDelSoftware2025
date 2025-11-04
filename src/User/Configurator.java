package User;
import java.util.*;
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
        this.cityOfResidence = UserTui.getString("Inserisci la città di residenza");
		this.birthYear = UserTui.getInteger("Inserisci l'anno di nascita", 1900, 2025);
        this.roleTitle = roleTitle;
        
        Client.getInstance().set_new_user(userId.toString(), password, cityOfResidence, birthYear);
        String newUserAnswer = Client.getInstance().make_server_request();
        if (JSONObject.extractBoolean(newUserAnswer, "loginSuccessful"))
        {
            userName = JSONObject.getJsonValue(newUserAnswer, "userID");
            Client.getInstance().setUserID(userName);
            Client.getInstance().setUserPassword(password);
            set_basic_app_configuration (); // configurazione base dell'app da fare al primo accesso del configuratore
		    new ConfiguratorMenu ();
        }
        else
        {
            System.out.println ("Errore nella creazione utente!");
        }
    }
 
    //secondo costruttore, per quando i dati vengono recuperati dal server
    public Configurator (String userName, String cityOfResidence, int birthYear, String password, String roleTitle)
	{
		this.userName = userName;
		this.cityOfResidence = cityOfResidence;
		this.birthYear = birthYear;
        this.password = password;
		this.roleTitle = roleTitle;
		
		new ConfiguratorMenu ();
	}
	
	// metodo per fissare l'ambito territoriale e il numero max di persone iscrivibili dal fruitore (Vedi punto 3, Versione 1)
	public void set_basic_app_configuration ()
	{
        // inserire controlli su associazioni già esistenti, invio al server dell'associazione
        String organizationName = UserTui.getString("\nCome si chiama l'organizzazione per cui lavori");
		ArrayList <String> areaOfInterest = UserTui.getStringArray("In che zona opera questa associazione (Inserire un luogo alla volta)", 
                                                                        "Vuoi inserire un'altra zona");
        Client.getInstance().set_new_organization(organizationName, areaOfInterest);
        String newOrganizationResponse = Client.getInstance().make_server_request();

        // dico all'utente che la richieste è riuscita o no
        
        int maxPeopleForSubscription = UserTui.getInteger("Quante persone un fruitore dell'applicazione può iscrivere con una sola iscrizione", 0, 100);
        JSONObjectCreator.setMaxPeopleForSubscription(maxPeopleForSubscription);
        
        make_new_places();    // metodo per creare nuovi luoghi per le visite
	}
	
    // metodo per la creazione dei posti da visitare (Vedi punto 3, Versione 1)
	public void make_new_places ()
	{
        boolean addAnotherPlaceAnswer;
        boolean addAnotherTypeVisitAnswer; 
        //attributi usati come discriminante del ciclo

        do
        {
            String cityName = UserTui.getStringNoTrim("Inserire la città dove si svolge questo evento");
            String cityAddress = UserTui.getStringNoTrim("Inserisci l'indirizzo");
            
            do 
            {
                String eventName = UserTui.getStringNoTrim ("Inserisci il nome dell'evento");
                String eventDescription = UserTui.getStringNoTrim("Inserisci una descrizione dell'evento", 500);

                // acquisizioni da migliorare
                String associationName = ""; 
                String tmpVoluntaryName = UserTui.getString("Inserire il volontario che segue questa visita");
                // ????
                // sulla scelta del volontario -> creare metodo che gli mostra quelli esistenti o che gli chiede di aggiugerne uno nuovo

                String visitType = UserTui.getString("Inserisci il tipo di visita");
                DataManagerPeriod date = new DataManagerPeriod();
                int startDate = date.getStartDate();
                int endDate = date.getEndDate();
                int minPartecipants = UserTui.getInteger("Inserisci il numero minimo di partecipanti a questo evento", 1, 1000);
                int maxPartecipants = UserTui.getInteger("Inserisci il numero massimo di partecipanti a questo evento", minPartecipants+1, 1000);
                int maxPeopleForSubscription = JSONObjectCreator.getMaxPeopleForSubscription();
                
                Client.getInstance().set_new_event(eventName, eventDescription, cityName, cityAddress, startDate, endDate, 
                associationName, minPartecipants, maxPartecipants, maxPeopleForSubscription, visitType);
                JSONObject.confirmRequest(Client.getInstance().make_server_request(), "registrationSuccesful");

                addAnotherTypeVisitAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro tipo di visita associato a questo luogo");
            } while (addAnotherTypeVisitAnswer); // fine ciclo tipo visita
            
            addAnotherPlaceAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro luogo");
        } while (addAnotherPlaceAnswer); // fine ciclo luogo
	}

  
	
}


