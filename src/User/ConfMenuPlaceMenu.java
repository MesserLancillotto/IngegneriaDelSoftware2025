package User;

import java.util.*;
import org.json.*;
import Client.Client;

public class ConfMenuPlaceMenu extends UserMenu
{
    private String organization;

    public void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> view_visitable_places());
        menuSelection.put(2, () -> view_type_of_visit_by_place());
        //iterazione 3
        menuSelection.put(3, () -> introduce_new_place());
        menuSelection.put(4, () -> add_to_existing_place_new_visit());
        menuSelection.put(5, () -> remove_place());
        menuSelection.put(6, () -> remove_visit_type_from_place()); 

        menuOptionList.add("Visualizza l'elenco dei luoghi visitabili");
        menuOptionList.add("Visualizza l'elenco dei tipi di visita associati a ciascun luogo");
    }

    public ConfMenuPlaceMenu (String organization)
    {
        printCenteredTitle("MENU GESTIONE POSTI VISITABILI");
        this.organization = organization;   
        initialize_menu_selection();
        manage_options();
    }

    public ConfMenuPlaceMenu (boolean isFirstAccess, String organization)
    {
        this.organization = organization;
        introduce_new_place();
    }

    public void view_visitable_places ()
	{
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        JSONArray eventsArray = new JSONArray(getEventResponse);

        Set <String> distinctPlaces = new HashSet <> ();
        for (int i = 0; i < getEventResponse.length(); i++)
        {
            JSONObject event = eventsArray.getJSONObject(i);
            StringBuilder place = new StringBuilder ();
            place.append(event.getString("city"));
            place.append (":");
            place.append(event.getString("address"));
            distinctPlaces.add(place.toString());
        }
		UserTui.stamp_list ("Questi sono i posti visitabili: ", distinctPlaces);
	}

    public void view_type_of_visit_by_place() 
    {
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        filters.put ("visitType", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        JSONArray eventsArray = new JSONArray(getEventResponse);
        
        Map<String, Place> placeMap = new HashMap<>();
        
        for (int i = 0; i < getEventResponse.length(); i++)
        {
            JSONObject event = eventsArray.getJSONObject(i);
            StringBuilder place = new StringBuilder ();
            place.append(event.getString("city"));
            place.append (":");
            place.append(event.getString("address"));
            String p = place.toString().toUpperCase();
            
            placeMap.putIfAbsent(p, new Place (p));
            placeMap.get (p).addVisitType(event.getString("visitType"));
        }
        UserTui.stamp_list("Ecco gli eventi associati a ciascun luogo", placeMap);
    }

    public void introduce_new_place()
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
                String visitType = UserTui.getString("Inserisci il tipo di visita");
                String meetingPoint = UserTui.getStringNoTrim ("Inserisci dove è il meeting point");
                ArrayList <String> visitDays = new ArrayList<>();
                ArrayList <Integer> startHours = new ArrayList<>();
                ArrayList <Integer> duration = new ArrayList<>();

                do
                {
                    visitDays.add (DataManager.getDayOfWeekFromUser("Inserisci il giorno della settimana in cui si svolge questa visita"));
                    startHours.add (DataManager.getAnHourFromUser("Inserisci l'orario di inizio di questa visita (formato HH:MM)"));
                    duration.add (UserTui.getInteger("Inserisci la durata in minuti di questa visita", 1, 1440));
                }while (UserTui.getYesNoAnswer("Vuoi inserire un altro giorno in cui si svolge questa visita"));

                DataManagerPeriod date = new DataManagerPeriod();
                int startDate = date.getStartDate();
                int endDate = date.getEndDate();
                int minPartecipants = UserTui.getInteger("Inserisci il numero minimo di partecipanti a questo evento", 1, 1000);
                int maxPartecipants = UserTui.getInteger("Inserisci il numero massimo di partecipanti a questo evento", minPartecipants+1, 1000);
                int maxPeopleForSubscription = JSONObjectCreator.getMaxPeopleForSubscription();
                
                Client.getInstance().set_new_event(eventName, eventDescription, cityName, cityAddress, meetingPoint, startDate, endDate, 
                organization, minPartecipants, maxPartecipants, maxPeopleForSubscription, visitType, visitDays, startHours, duration);
                String setNewEventReply = Client.getInstance().make_server_request();
                JSONObject dictionary = new JSONObject(setNewEventReply);
                UserTui.operationIsSuccessful(dictionary.getBoolean("registrationSuccesful"));

                addAnotherTypeVisitAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro tipo di visita associato a questo luogo");
            } while (addAnotherTypeVisitAnswer); // fine ciclo tipo visita
            
            addAnotherPlaceAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro luogo");
        } while (addAnotherPlaceAnswer); // fine ciclo luogo
    }

    public void add_to_existing_place_new_visit()
    {
        //DA IMPLEMENTARE
    }  

    public void remove_place()
    {
        //DA IMPLEMENTARE
    }

    public void remove_visit_type_from_place()
    {
        //DA IMPLEMENTARE
    }   
}
