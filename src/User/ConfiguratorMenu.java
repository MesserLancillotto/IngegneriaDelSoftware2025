package User;
import java.util.*;
import java.time.*;
import java.time.format.*;
import Client.Client;

public class ConfiguratorMenu implements UserMenu
{
    public void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> view_voluntary_list());
        menuSelection.put(2, () -> view_visitable_places());
        menuSelection.put(3, () -> view_type_of_visit_by_place());
        menuSelection.put(4, () -> view_visit_state());  
        menuSelection.put(5, () -> modify_max_number_per_subscription());
        menuSelection.put(6, () -> manage_disponibilty_dates());  

        menuOptionList.add("Visualizza l'elenco volontari");
        menuOptionList.add("Visualizza l'elenco dei luoghi visitabili");
        menuOptionList.add("Visualizza l'elenco dei tipi di visita associati a ciascun luogo");
        menuOptionList.add("Visualizza le visite in stato di visita");	
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
    public ConfiguratorMenu ()
    {
        initialize_menu_selection();
        manage_options();
    }

    public void view_visitable_places ()
	{
        ArrayList <Place> placeList = new ArrayList <>();
        //chiamata al server per caricare tutti i posti disponibili
        

		Set <String> distinctPlaces = new HashSet <> ();
		for (Place p : placeList)
		{
			distinctPlaces.add (p.placeName);
		}
		UserTui.stamp_list ("Questi sono i posti visitabili: ", distinctPlaces);
	}
	
	public void view_type_of_visit_by_place ()
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
        JSONObjectCreator.setMaxPeopleForSubscription(newMaxNumber);
    }

    public void view_voluntary_list()
    {
        Client.getInstance().get_voluntaries_for_visit("");
        String getVoluntariesResponse = Client.getInstance().make_server_request();
        Set <String> voluntaryList = new HashSet <>(JSONObject.extractArray(getVoluntariesResponse, "userID"));
        UserTui.stamp_list("Ecco l'elenco dei volontari iscritti:", voluntaryList);
    }

    public void manage_disponibilty_dates ()
    {
        boolean addAnotherDate;
        DataManagerDisponibility date = new DataManagerDisponibility(3);
        do
        {
            int unaviableDay = date.getReferenceDay("Inserire il giorno precluso alle visite", "Di quanti giorni è la chiusura");
            if (unaviableDay > 0)
            {
                int unixDate = (int)date.getUnixDate(unaviableDay);
                Client.getInstance().set_closed_days(unixDate, date.getEndDayOfClosure(), "ASSOCIAZIONE");  // definisci ASSOCIAZIONE
                String closedDaysReply = Client.getInstance().make_server_request();
                JSONObject.confirmRequest (closedDaysReply, "querySuccesful");
            }
            addAnotherDate = UserTui.getYesNoAnswer("Vuoi inserire un'altra data");
        }while (addAnotherDate);
    }

    private void view_visit_state ()
    {

    }
}
