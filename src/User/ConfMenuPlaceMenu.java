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
        menuOptionList.add("Inserisci un nuovo luogo visitabile");
        menuOptionList.add("Aggiungi un nuovo tipo di visita a un luogo esistente");
        menuOptionList.add("Rimuovi un luogo visitabile");
        menuOptionList.add("Rimuovi un tipo di visita da un luogo visitabile");
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

    public Set<String> view_visitable_places ()     // stampa i posti visitabili, return di Set<String> per poter riusare il metodo nella ricerca posti
	{
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        JSONArray eventsArray = new JSONArray(getEventResponse);

        Set <String> distinctPlaces = new HashSet <> ();
        for (int i = 0; i < eventsArray.length(); i++)
        {
            JSONObject event = eventsArray.getJSONObject(i);
            StringBuilder place = new StringBuilder ();
            place.append(event.getString("city"));
            place.append (":");
            place.append(event.getString("address"));
            distinctPlaces.add(place.toString());
        }
		UserTui.stamp_list ("Questi sono i posti visitabili: ", distinctPlaces);
        return distinctPlaces;
	}

    public Map<String, Place> view_type_of_visit_by_place() 
    {
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        filters.put ("visitType", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        if (JSONObjectMethod.isValidJSONArray(getEventResponse))
        {
            JSONArray eventsArray = new JSONArray(getEventResponse);
        
            Map<String, Place> placeMap = new HashMap<>();
            
            for (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                StringBuilder place = new StringBuilder ();
                place.append(event.getString("city"));
                place.append (":");
                place.append(event.getString("address"));
                String p = place.toString().toUpperCase().trim();   // normalizzo foramto per evitare duplicati
                
                placeMap.putIfAbsent(p, new Place (place.toString())); // creo una nuova istanza di Place se non esiste già con combo città+indirizzo in maisucolo e trim
                placeMap.get(p).addVisitType(event.getString("visitType"));
            }
            UserTui.stamp_list("Ecco gli eventi associati a ciascun luogo", placeMap);
            return placeMap;
        }
        return null;
    }

    public void introduce_new_place()
    {
        boolean addAnotherPlaceAnswer;
        do
        {
            String cityName = UserTui.getStringNoTrim("Inserire la città dove si svolge questo evento");
            String cityAddress = UserTui.getStringNoTrim("Inserisci l'indirizzo");
            add_place_to_server(cityName, cityAddress);
            addAnotherPlaceAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro luogo");
        } while (addAnotherPlaceAnswer); // fine ciclo luogo
    }

    public void add_to_existing_place_new_visit()
    {
        Set<String> placesToChoose = view_visitable_places();
        StringBuilder place;
        String city = "";
        String address = "";
        boolean continueSearching = true;
        boolean placeIsValid = false;
        while (continueSearching)
        {
            place = new StringBuilder ();
            city = UserTui.getStringNoTrim("Inserire la città dove si svolge questo evento");
            place.append(city);
            place.append(":");
            address = UserTui.getStringNoTrim("Inserisci l'indirizzo");
            place.append(address);
            String researchString = place.toString().toUpperCase().trim(); // normalizzo foramto per evitare duplicati
            placeIsValid = placesToChoose.contains(researchString);
            if (!placeIsValid)
                continueSearching = UserTui.getYesNoAnswer("Il luogo inserito non è valido. Vuoi riprovare?");
        }
        if (placeIsValid)
        {
            if (!city.equals("") && !address.equals(""))
                add_place_to_server(city, address);
        }
    }  

    
    public void remove_place()  //DA FINIRE IMPLEMENTAZIONE
    {
        HashMap <Integer, String> placesToChoose = UserTui.fromListToMap(view_visitable_places());
        String placeToRemove = UserTui.getChoiceFromMap("Scegli il luogo da rimuovere", placesToChoose);

        if (!placeToRemove.trim().isEmpty())
       {
             //Client.getInstance().remove_place(placeToRemove);
            String removePlaceReply = Client.getInstance().make_server_request();
            JSONObject dictionary = new JSONObject(removePlaceReply);
            UserTui.operationIsSuccessful(dictionary.getBoolean("querySuccesful"));
       }
    }

    public void remove_visit_type_from_place()  //DA FINIRE IMPLEMENTAZIONE
    {
        HashMap <Integer, String> placesToChoose = UserTui.fromListToMap(view_visitable_places());
        String placeToRemove = UserTui.getChoiceFromMap("Scegli da quale luogo rimuovere il tipo di visita", placesToChoose);

        String visitTypeToRemove = get_visit_type_from_place(placeToRemove);
        if (!placeToRemove.trim().isEmpty() && !visitTypeToRemove.trim().isEmpty())
        {
            //Client.getInstance().remove_visit_type_from_place(placeToRemove, visitTypeToRemove);
            String removeVisitTypeReply = Client.getInstance().make_server_request();
            JSONObject dictionary = new JSONObject(removeVisitTypeReply);
            UserTui.operationIsSuccessful(dictionary.getBoolean("querySuccesful"));
        }

    }   

    //METODI GESTIONE INTERNA

    private String get_visit_type_from_place (String place)
    {
        Map<String, Place> placeMap = view_type_of_visit_by_place();
        Set <String> visitTypes = new HashSet<>();
        String visitTypeToRemove = "";
        if (placeMap != null)
        {
            Place p = placeMap.get(place.toUpperCase().trim());
            if (p != null)
            {
                visitTypes = p.get_type_visit_list();
                visitTypeToRemove = UserTui.getChoiceFromMap("Scegli quale tipo di visita rimuovere", UserTui.fromListToMap(visitTypes));
            }
        }
        return visitTypeToRemove;
    }

    private void add_place_to_server(String cityName, String cityAddress)
    {
        boolean addAnotherTypeVisitAnswer; 
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
                float price = 0;
                if (UserTui.getYesNoAnswer("Questo evento è a pagamento?"))
                    price = UserTui.getFloat("Inserisci il prezzo in euro di questo evento", 0, 10000);
                
                Client.getInstance().set_new_event(eventName, eventDescription, cityName, cityAddress, meetingPoint, startDate, endDate, 
                organization, minPartecipants, maxPartecipants, maxPeopleForSubscription, visitType, price, visitDays, startHours, duration);
                String setNewEventReply = Client.getInstance().make_server_request();
                JSONObject dictionary = new JSONObject(setNewEventReply);
                UserTui.operationIsSuccessful(dictionary.getBoolean("registrationSuccesful"));

                addAnotherTypeVisitAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro tipo di visita associato a questo luogo");
            } while (addAnotherTypeVisitAnswer); // fine ciclo tipo visita
    }
}
