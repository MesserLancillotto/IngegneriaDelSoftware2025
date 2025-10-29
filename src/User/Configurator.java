package User;
import java.util.*;
import Client.Client;

public class Configurator extends User
{	
    // costruttore per primo accesso del configuratore
    public Configurator (String tmpUserName, String tmpPassword)
    {
        StringBuilder userId = new StringBuilder();
        userId.append(UserTui.getStringNoTrim("Inserisci il nome: "));
        userId.append (" ");
        userId.append(UserTui.getStringNoTrim("Inserisci il cognome: "));
        set_new_password();
        this.cityOfResidence = UserTui.getString("Inserisci la città di residenza");
		this.birthYear = UserTui.getInteger("Inserisci l'anno di nascita", 1900, 2025);
        this.roleTitle = "C_";
        
        String newUserRequest = Client.newUser(tmpUserName, tmpPassword, userId.toString(), this.password, this.cityOfResidence, this.birthYear, this.roleTitle);
        String newUserAnswer = Client.makeServerRequest(Client.getServerAddr(), Client.getPort(), newUserRequest);
        if (JSONObject.extractBoolean(newUserAnswer, "loginSuccessful"))
        {
            userName = JSONObject.extractUserID(newUserAnswer);
            set_basic_app_configuration (); // configurazione base dell'app da fare al primo accesso del configuratore
		    new ConfiguratorMenu (userName, password);
        }
        else
        {
            System.out.println ("Errore nella creazione utente!");
        }
    }
 
    //secondo costruttore, per quando i dati vengono recuperati dal server
    public Configurator (String userName, String cityOfResidence, int birthYear, String password)
	{
		this.userName = userName;
		this.cityOfResidence = cityOfResidence;
		this.birthYear = birthYear;
        this.password = password;
		this.roleTitle = "C_";
		
		new ConfiguratorMenu (userName, password);
	}
	
	// metodo per fissare l'ambito territoriale e il numero max di persone iscrivibili dal fruitore (Vedi punto 3, Versione 1)
	public void set_basic_app_configuration ()
	{
		String areaOfInterest = UserTui.getString("\nIn che ambito territoriale opera l'applicazione");
        //client.makeServerRequest(areaOfInterest, maxPeopleForSubscription, areaOfInterest)
        
        int maxPeopleForSubscription = UserTui.getInteger("Quante persone un fruitore dell'applicazione può iscrivere con una sola iscrizione", 0, 100);
        //client.makeServerRequest(areaOfInterest, maxPeopleForSubscription, areaOfInterest)
        
        make_new_association();    // metodo per creare nuovi luoghi per le visite
	}
	
    // metodo per la creazione delle prime associazioni (Vedi punto 3, Versione 1)
	public void make_new_association ()
	{
        String tmpVisitType;
        String tmpVoluntaryName;

        boolean addAnotherPlaceAnswer;
        boolean addAnotherTypeVisitAnswer; 
        //attributi usati come discriminante del ciclo

        do
        {
            String tmpPlace = UserTui.getStringNoTrim("Inserire un nuovo luogo per una visita guidata");
            
            
            do 
            {
                tmpVisitType = UserTui.getStringNoTrim("Inserire il tipo di visita che offre il luogo");
                tmpVoluntaryName = UserTui.getString("Inserire il volontario che segue questa visita");
                // sulla scelta del volontario -> creare metodo che gli mostra quelli esistenti o che gli chiede di aggiugerne uno nuovo
                
                //client.makeServerRequest(areaOfInterest, maxPeopleForSubscription, areaOfInterest)  
                //invio al server il nuovo Place da aggiungere al database

                addAnotherTypeVisitAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro tipo di visita associato a questo luogo");
            } while (addAnotherTypeVisitAnswer); // fine ciclo tipo visita
            
            System.out.printf ("Vuoi inserire un'altro luogo: ");
            addAnotherPlaceAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro luogo");
        } while (addAnotherPlaceAnswer); // fine ciclo luogo
	}

  
	
}


