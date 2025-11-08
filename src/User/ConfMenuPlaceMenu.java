package User;

import java.util.*;
import org.json.*;
import Client.Client;

public class ConfMenuPlaceMenu extends UserMenu
{
    private static final String MENU_TITLE = "MENU GESTIONE POSTI VISITABILI";
    private static final String ERROR_CONNECTION_SERVER = "Errore! Impossibile contattare il server";
    private static final int MAX_PEOPLE_FOR_VISIT = 2000;
    private static final int MAX_VISIT_PRICE = 10000;
    private static final int MAX_VISIT_DURATION = 1440;
    private static final int MIN_RANGE_VALUE = 0;
    private static final int MIN_VISIT_DURATION = 59;
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
        menuOptionList.add("Inserisci una nuova visita in un nuovo posto");
        menuOptionList.add("Inserisci una nuova visita in un luogo esistente");
        menuOptionList.add("Rimuovi un luogo visitabile (e le visite ad esso associate)");
        menuOptionList.add("Rimuovi un tipo di visita da un luogo visitabile");
    }

    public ConfMenuPlaceMenu (String organization)
    {
        printCenteredTitle(MENU_TITLE);
        this.organization = organization;   
        initialize_menu_selection();
        UserTui.stampSeparator();
        manage_options(MENU_TITLE);
    }
    // COSTRUTTORE PER IL PRIMO ACCESSO DEL CONFIGURATORE
    public ConfMenuPlaceMenu (boolean isFirstAccess, String organization)
    {
        this.organization = organization;
        introduce_new_place();
    }
    // stampa i posti visitabili, return di Set<String> per poter riusare il metodo nella ricerca posti
    public Map<String, String> view_visitable_places ()     //OK
	{
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        if (!getEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getEventResponse))
        {
            JSONArray eventsArray = new JSONArray(getEventResponse);
            Map<String, String> distinctPlaces = new HashMap<>();
            for (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                StringBuilder place = new StringBuilder ();
                place.append(event.getString("city"));
                place.append (":");
                place.append(event.getString("address"));
                String formattedPlace = place.toString().toUpperCase().trim().replaceAll(" ", "");
                distinctPlaces.putIfAbsent(formattedPlace, place.toString());
            }
            UserTui.stamp_list ("Questi sono i posti visitabili: ", distinctPlaces.values());
            return distinctPlaces;
        }
        return null;
	}

    public Map<String, Place> view_type_of_visit_by_place() //OK
    {
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        filters.put ("visitType", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        if (!getEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getEventResponse))
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
                String formattedPlace = place.toString().toUpperCase().trim().replaceAll(" ", "");   // normalizzo formato per evitare duplicati
                
                placeMap.putIfAbsent(formattedPlace, new Place (place.toString())); // creo una nuova istanza di Place se non esiste già con combo città+indirizzo in maisucolo e trim
                placeMap.get(formattedPlace).addVisitType(event.getString("visitType"));
            }
            UserTui.stamp_list("Ecco gli eventi associati a ciascun luogo", placeMap);
            return placeMap;
        }
        else
            System.out.println (ERROR_CONNECTION_SERVER);
        return null;
    }

    public void introduce_new_place()   //OK
    {
        boolean addAnotherPlaceAnswer;
        do
        {
            String cityName = UserTui.getString("Inserire la città dove si svolge questo evento");
            String cityAddress = UserTui.getString("Inserisci l'indirizzo");
            add_place_to_server(cityName, cityAddress);
            addAnotherPlaceAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro luogo");
        } while (addAnotherPlaceAnswer); 
    }

    public void add_to_existing_place_new_visit()   //OK
    {
        Map<String, String> placesToChoose = get_visitable_places();
        if (placesToChoose != null && !placesToChoose.isEmpty())
        {
            HashMap <Integer, String> placesToChooseMap = UserTui.fromListToMap(placesToChoose.values());
            String place = UserTui.getChoiceFromMap("Scegli in che luogo aggiungere una nuova visita", placesToChooseMap);
            boolean confirmDecision = UserTui.getYesNoAnswer ("Hai scelto "+ place+ " confermi ");
            if (!place.trim().isEmpty() && confirmDecision)
            {
                int commaPlace = place.indexOf(":");
                String city = place.substring(0, commaPlace);
                String address = place.substring(commaPlace+1);
                add_place_to_server(city, address);
            }
        }
        else
            System.out.println (ERROR_CONNECTION_SERVER);
    }

    
    public void remove_place()  //fai un secondo check ma dovrebbe funzionare
    {
        Map<String, String> placesToChoose = view_visitable_places();
        if (placesToChoose != null && !placesToChoose.isEmpty())
        {
            HashMap <Integer, String> placesToChooseMap = UserTui.fromListToMap(placesToChoose.values());
            String placeToRemove = UserTui.getChoiceFromMap("Scegli il luogo da rimuovere", placesToChooseMap);
            boolean confirmDecision = UserTui.getYesNoAnswer ("Hai scelto "+ placeToRemove+ " confermi ");
            if (!placeToRemove.trim().isEmpty() && confirmDecision)
            {
                int commaPlace = placeToRemove.indexOf(":");
                String cityToRemove = placeToRemove.substring(0, commaPlace);
                String addressToRemove = placeToRemove.substring(commaPlace+1);
                Client.getInstance().delete_place(cityToRemove, addressToRemove);
                String removePlaceReply = Client.getInstance().make_server_request();
                if (!removePlaceReply.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(removePlaceReply))
                {
                    JSONObject dictionary = new JSONObject(removePlaceReply);
                    boolean removeSuccess = dictionary.getBoolean("loginSuccessful");
                    UserTui.operationIsSuccessful(removeSuccess);
                    if (removeSuccess)
                    {
                        int numberOfVisitRemoved = dictionary.getInt("rowsDeleted");
                        StringBuilder numberOfVisitRemovedCommunication = new StringBuilder();
                        numberOfVisitRemovedCommunication.append("Hai rimosso ");
                        numberOfVisitRemovedCommunication.append(numberOfVisitRemoved);
                        numberOfVisitRemovedCommunication.append(" visite");
                        System.out.println (numberOfVisitRemovedCommunication.toString());
                    }
                }
                else
                    System.out.println (ERROR_CONNECTION_SERVER);
            }
        }
        else
            System.out.println (ERROR_CONNECTION_SERVER);
    }

    public void remove_visit_type_from_place()  //manca solo chiamata al server
    {
        Map<String, String> placesToChoose = view_visitable_places();
        if (placesToChoose != null && !placesToChoose.isEmpty())
        {
            HashMap <Integer, String> placesToChooseMap = UserTui.fromListToMap(placesToChoose.values());
            String placeToRemove = UserTui.getChoiceFromMap("Scegli da quale luogo rimuovere il tipo di visita", placesToChooseMap);
            boolean confirmDecision = UserTui.getYesNoAnswer ("Hai scelto "+ placeToRemove+ " confermi ");
            if (!placeToRemove.trim().isEmpty() && confirmDecision)
            {
                String visitTypeToRemove = get_visit_type_from_place(placeToRemove);
                if (!placeToRemove.trim().isEmpty() && !visitTypeToRemove.trim().isEmpty())
                {
                    //Client.getInstance().remove_visit_type_from_place(placeToRemove, visitTypeToRemove);
                    String removeVisitTypeReply = Client.getInstance().make_server_request();
                    if (!removeVisitTypeReply.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(removeVisitTypeReply))
                    {
                        JSONObject dictionary = new JSONObject(removeVisitTypeReply);
                        UserTui.operationIsSuccessful(dictionary.getBoolean("querySuccesful")); // CONTROLLA key
                    }
                }
            }
        }
        else
            System.out.println (ERROR_CONNECTION_SERVER);
    }   

    //METODI GESTIONE INTERNA

    public Map<String, String> get_visitable_places ()     //OK
	{
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        if (!getEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getEventResponse))
        {
            JSONArray eventsArray = new JSONArray(getEventResponse);
            Map<String, String> distinctPlaces = new HashMap<>();
            for (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                StringBuilder place = new StringBuilder ();
                place.append(event.getString("city"));
                place.append (":");
                place.append(event.getString("address"));
                String formattedPlace = place.toString().toUpperCase().trim().replaceAll(" ", "");
                distinctPlaces.putIfAbsent(formattedPlace, place.toString());
            }
            return distinctPlaces;
        }
        return null;
	}

    public Map<String, Place> get_type_of_visit_by_place() //OK
    {
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        filters.put ("visitType", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        if (!getEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getEventResponse))
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
                String formattedPlace = place.toString().toUpperCase().trim().replaceAll(" ", "");   // normalizzo formato per evitare duplicati
                
                placeMap.putIfAbsent(formattedPlace, new Place (place.toString())); // creo una nuova istanza di Place se non esiste già con combo città+indirizzo in maisucolo e trim
                placeMap.get(formattedPlace).addVisitType(event.getString("visitType"));
            }
            return placeMap;
        }
        else
            System.out.println (ERROR_CONNECTION_SERVER);
        return null;
    }

    private String get_visit_type_from_place (String stampablePlace)
    {
        String place = stampablePlace.toUpperCase().trim().replaceAll(" ", "");
        Map<String, Place> placeMap = get_type_of_visit_by_place();
        String visitTypeToRemove = "";
        if (placeMap != null && !placeMap.isEmpty())
        {
            Set <String> visitTypes = new HashSet<>();
            Place p = placeMap.get(place);  // place passato come .toUpperCase().trim().replaceAll(" ", "")
            if (p != null)
            {
                visitTypes = p.get_type_visit_list();
                visitTypeToRemove = UserTui.getChoiceFromMap("Scegli quale tipo di visita rimuovere da "+stampablePlace, UserTui.fromListToMap(visitTypes));
            }
            
        }
        return visitTypeToRemove;
    }

    private void add_place_to_server(String cityName, String cityAddress) // DA CONTROLLARE
    {
        boolean addAnotherTypeVisitAnswer; 
        do 
            {
                String eventName = UserTui.getString("Inserisci il nome dell'evento");
                String eventDescription = UserTui.getString("Inserisci una descrizione dell'evento", 500);
                String visitType = UserTui.getString("Inserisci il tipo di visita");
                String meetingPoint = UserTui.getString("Inserisci dove è il meeting point");
                DataManagerPeriod date = new DataManagerPeriod();
                int startDate = date.getStartDate();
                int endDate = date.getEndDate();
                ArrayList <String> visitDays = new ArrayList<>();
                ArrayList <Integer> startHours = new ArrayList<>();
                ArrayList <Integer> duration = new ArrayList<>();
                do
                {
                    String visitDay = DataManager.getDayOfWeekFromUser("Inserisci il giorno della settimana in cui si svolge questa visita");
                    if (!visitDay.trim().isEmpty()) // controllo che sia andata bene l'acquisizione
                    {
                        visitDays.add(visitDay);
                        startHours.add (DataManager.getAnHourFromUser("Inserisci l'orario di inizio di questa visita (formato HH:MM)"));
                        duration.add (UserTui.getInteger("Inserisci la durata in minuti di questa visita (minimo 60 minuti)", MIN_VISIT_DURATION, MAX_VISIT_DURATION));
                    }
                }while (UserTui.getYesNoAnswer("Vuoi inserire un altro giorno in cui si svolge questa visita"));
                int minPartecipants = UserTui.getInteger("Inserisci il numero minimo di partecipanti a questo evento", MIN_RANGE_VALUE, MAX_PEOPLE_FOR_VISIT);
                int maxPartecipants = UserTui.getInteger("Inserisci il numero massimo di partecipanti a questo evento", minPartecipants+1, MAX_PEOPLE_FOR_VISIT);
                int maxPeopleForSubscription = JSONObjectCreator.getMaxPeopleForSubscription();
                float price = 0;
                if (UserTui.getYesNoAnswer("Questo evento è a pagamento?"))
                    price = UserTui.getFloat("Inserisci il prezzo in euro di questo evento", MIN_RANGE_VALUE, MAX_VISIT_PRICE);
                
                UserTui.stampAllEventInfo(cityName, cityAddress, eventName, eventDescription, visitType, meetingPoint, visitDays, startHours, duration, startDate, endDate, minPartecipants, maxPartecipants, maxPeopleForSubscription, price);
                if (UserTui.getYesNoAnswer("Confermi "))
                {
                    Client.getInstance().set_new_event(eventName, eventDescription, cityName, cityAddress, meetingPoint, startDate, endDate, 
                    organization, minPartecipants, maxPartecipants, maxPeopleForSubscription, visitType, price, visitDays, startHours, duration);
                    String setNewEventReply = Client.getInstance().make_server_request();
                    if (!setNewEventReply.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(setNewEventReply))
                    {
                        JSONObject dictionary = new JSONObject(setNewEventReply);
                        UserTui.operationIsSuccessful(dictionary.getBoolean("registrationSuccesful"));
                    }
                    else
                        System.out.println (ERROR_CONNECTION_SERVER);

                }
                addAnotherTypeVisitAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro tipo di visita associato a questo luogo");
            } while (addAnotherTypeVisitAnswer); // fine ciclo tipo visita
    }
}
