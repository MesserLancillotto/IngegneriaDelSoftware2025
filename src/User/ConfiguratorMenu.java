package User;
import java.util.*;
import java.time.*;
import java.time.format.*;
import Client.Client;

public class ConfiguratorMenu implements UserMenu
{
    private String configuratorUserName;
    private String configuratorPassword;

    public void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> view_voluntary_list());
        menuSelection.put(2, () -> view_visitable_places());
        menuSelection.put(3, () -> view_type_of_visit());
        menuSelection.put(4, () -> view_visit_state());  
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

    public void manage_options ()
	{
        boolean keepUsingConfiguratorMenu;
        do
        {
            visualize_options();

            System.out.printf ("\nVuoi fare altro: ");
            keepUsingConfiguratorMenu = UserTui.getYesNoAnswer("\nVuoi fare altro");
        }while (keepUsingConfiguratorMenu);
	}

    //costruttore
    public ConfiguratorMenu (String configuratorUserName, String configuratorPassword)
    {
        this.configuratorUserName = configuratorUserName;
        this.configuratorPassword = configuratorPassword;

        initialize_menu_selection();
        manage_options();
    }

    public void view_visitable_places ()
	{
        ArrayList <Place> placeList = new ArrayList <>();
        //chiamata al server per caricare tutti i posti disponibili
        String viewVisitablePlacesRequest = Client.getVisitablePlaces (configuratorUserName, configuratorPassword, "CITTA", "");
        String viewVisitablePlacesResponse = Client.makeServerRequest(Client.getServerAddr(), Client.getPort(), viewVisitablePlacesRequest);

		Set <String> distinctPlaces = new HashSet <> ();
		for (Place p : placeList)
		{
			distinctPlaces.add (p.placeName);
		}
		UserTui.stamp_list ("Questi sono i posti visitabili: ", distinctPlaces);
	}
	
	public void view_type_of_visit ()
	{
        ArrayList <Place> placeList = new ArrayList <>();
        //chiamata al server per caricare tutti i posti disponibili

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
        boolean addAnotherDate;
        DataManager date = new DataManager();
        do
        {
            int unaviableDay = date.getUnaviableDay();
            if (unaviableDay > 0)
            {
                int unixDate = (int)date.getUnixDate(unaviableDay);
                String setClosedDayRequest = Client.setClosedDays (configuratorUserName, configuratorPassword, 
                                                                    unixDate, date.setToEndOfDay(unixDate), "ORGANIZZAZIONE");
            }
            addAnotherDate = UserTui.getYesNoAnswer("Vuoi inserire un'altra data");
        }while (addAnotherDate);
    }

    private void view_visit_state ()
    {

    }
}
