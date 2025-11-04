package User;
import java.util.*;
import java.time.*;
import java.time.format.*;
import Client.Client;

public class ConfiguratorMenu implements UserMenu
{
    private ArrayList <Visit> visitList = new ArrayList<>();

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

    //COSTRUTTORE
    public ConfiguratorMenu ()
    {
        initialize_menu_selection();
        manage_options();
    }

    public void view_visitable_places ()
	{
        Client.getInstance().get_event(null);
        String getEventResponse = Client.getInstance().make_server_request();
        visitList = JSONObject.getVisitArray (getEventResponse);

		Set <String> distinctPlaces = new HashSet <> ();
        for (Visit tmpVisit : visitList)
        {
            distinctPlaces.add(tmpVisit.getPlace());
        }
		UserTui.stamp_list ("Questi sono i posti visitabili: ", distinctPlaces);
	}
	
    public void view_type_of_visit_by_place() 
    {
        //HashMap<String, Object> filters = {"city":"%", "address":"%", "visitType":"%", "organization":"San Genesio"};
        Client.getInstance().get_event(null);
        String getEventResponse = Client.getInstance().make_server_request();
        visitList = JSONObject.getVisitArray(getEventResponse);
        
        Map<String, Place> placeMap = new HashMap<>();
        
        for (Visit tmpVisit : visitList) 
        {
            String tmpPlace = tmpVisit.getPlace();
            
            // Se il place non esiste nella mappa, lo crea
            placeMap.putIfAbsent(tmpPlace.toUpperCase(), new Place(tmpPlace));
            
            // Aggiunge il tipo di visita
            placeMap.get(tmpPlace.toUpperCase()).addVisitType(tmpVisit.getVisitType());
        }
        UserTui.stamp_list("Ecco gli eventi associati a ciascun luogo", placeMap);
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
        Client.getInstance().get_event(null);
        String getEventResponse = Client.getInstance().make_server_request();
        visitList = JSONObject.getVisitArray (getEventResponse);

        System.out.println ("\nEcco l'elenco delle visite attualmente nello stato di proposto/completato/confermato/cancellato/effettuato");
        for (Visit v: visitList)
        {
            if (v.getVisitState() == StateOfVisit.CANCELLATA && v.getVisitState() == StateOfVisit.PROPOSTA &&
                        v.getVisitState() == StateOfVisit.COMPLETA && v.getVisitState() == StateOfVisit.CONFERMATA && v.getVisitState() == StateOfVisit.EFFETTUATA)
            {
                System.out.printf ("La visita %s, che si tiene a %s ed è nello stato %s\n", v.getEventName(), v.getPlace(), v.getVisitState().toString());
            }
        }
    }
}
