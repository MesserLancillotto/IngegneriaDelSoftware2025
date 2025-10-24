import java.util.*;

public class Configurator extends User
{
    ArrayList <Place> placeList = new ArrayList <> ();
	
    HashMap <Integer, Runnable> menuSelection = new HashMap <>();
    ArrayList <String> menuOptionList = new ArrayList<>();

    private void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> view_voluntary_list());
        menuSelection.put(2, () -> view_visitable_places());
        menuSelection.put(3, () -> view_type_of_visit());
        menuSelection.put(4, () -> view_type_of_visit());   // metodo per visualizzare le visite in stato di visita
        menuSelection.put(5, () -> modify_max_number_per_subscription());
        menuSelection.put(6, () -> manage_disponibilty_dates());  

        menuOptionList.add("Visualizza l'elenco volontari");
        menuOptionList.add("Visualizza l'elenco dei luoghi visitabili");
        menuOptionList.add("Visualizza l'elenco dei tipi di visita associati a ciascun luogo");
        menuOptionList.add("Visualizza le visite in stato di visita");	// non ho capito cosa vuole
        menuOptionList.add("Modifica il numero massimo di persone iscrivibili a un'iniziativa da parte di un fruitore");
        menuOptionList.add("Segna date precluse alle visite");
    }
	
	public void visualize_options ()
	{
        int optionCount = 1;
        for (String options : menuOptionList)
        {
            System.out.println (optionCount+options);
        }
        menuSelection.get(UserTui.getInteger("Cosa vuoi fare", 0, optionCount+1))
                            .run();
	}
	
    // costruttore per primo accesso del configuratore
    public Configurator (String userName, String password)
    {
        this.userName = userName;
        this.password = password;

        this.cityOfResidence = UserTui.getString("Inserisci la città di residenza");
		this.birthYear = UserTui.getInteger("Inserisci l'anno di nascita", 1900, 2025);
        this.roleTitle = "C_";

        //client.makeServerRequest();
        //invio al server i dati di questo account

        initialize_menu_selection();
		set_basic_app_configuration (); // configurazione base dell'app da fare al primo accesso del configuratore
		manage_options ();
    }

    //secondo costruttore, per quando i dati vengono recuperati dal server
    public Configurator (String userName, String cityOfResidence, int birthYear, String password)
	{
		this.userName = userName;
		this.cityOfResidence = cityOfResidence;
		this.birthYear = birthYear;
        this.password = password;
		this.roleTitle = "C_";
		
        initialize_menu_selection();
		manage_options ();
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
	
	public void manage_options ()
	{
        String keepUsingConfiguratorMenu;
        do
        {
            visualize_options();

            System.out.printf ("\nVuoi fare altro: ");
            keepUsingConfiguratorMenu = UserTui.getYesNoAnswer("\nVuoi fare altro");
        }while (keepUsingConfiguratorMenu.toUpperCase().toUpperCase().equals ("SI"));
	}
	
    // metodo per la creazione delle prime associazioni (Vedi punto 3, Versione 1)
	public void make_new_association ()
	{
        String tmpVisitType;
        String tmpVoluntaryName;

        String addAnotherPlaceAnswer;
        String addAnotherTypeVisitAnswer; 
        //attributi usati come discriminante del ciclo

        do
        {
            String tmpPlace = UserTui.getStringNoTrim("Inserire un nuovo luogo per una visita guidata");
            
            
            do 
            {
                tmpVisitType = UserTui.getStringNoTrim("Inserire il tipo di visita che offre il luogo");
                tmpVoluntaryName = UserTui.getString("Inserire il volontario che segue questa visita");
                // sulla scelta del volontario -> creare metodo che gli mostra quelli esistenti o che gli chiede di aggiugerne uno nuovo
                
                placeList.add(new Place (tmpPlace, tmpVisitType, tmpVoluntaryName));
                //client.makeServerRequest(areaOfInterest, maxPeopleForSubscription, areaOfInterest)  
                //invio al server il nuovo luogo

                addAnotherTypeVisitAnswer = UserTui.getYesNoAnswer ("Vuoi inserire un'altro tipo di visita associato a questo luogo(SI/NO)");
            } while (addAnotherTypeVisitAnswer.toUpperCase().equals("SI")); // fine ciclo tipo visita
            
            System.out.printf ("Vuoi inserire un'altro luogo: ");
            addAnotherPlaceAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro luogo");
        } while (addAnotherPlaceAnswer.toUpperCase().equals ("SI")); // fine ciclo luogo
	}
	
	public void view_visitable_places ()
	{
		Set <String> distinctPlaces = new HashSet <> ();
		for (Place p : placeList)
		{
			distinctPlaces.add (p.placeName);
		}
		UserTui.stamp_list ("Questi sono i posti visitabili: ", distinctPlaces);
	}
	
	public void view_type_of_visit ()
	{
		for (Place p: placeList)
		{
			String msg = "Ecco i tipi di visita associati a" + p.placeName+":"; 
			UserTui.stamp_list (msg, p.get_type_visit_list());
		}
	}    

    public void modify_max_number_per_subscription()
    {
        int newMaxNumber = UserTui.getInteger("\nDefinire il nuovo numero massimo di persone che un fruitore può iscrivere in una volta sola", 0, 100);
        //client.makeServerRequest(password, newMaxNumber, cityOfResidence);
        //invio al server il nuovo valore
    }

    public void view_voluntary_list()
    {
        Set <String> voluntaryList = new HashSet <>();
        //client.makeServerRequest(password, birthYear, cityOfResidence);
        //chiedo al server di mandarmi i voluntary
        UserTui.stamp_list("Ecco l'elenco dei volontari iscritti:", voluntaryList);
    }

    public void manage_disponibilty_dates ()
    {

    }
}


