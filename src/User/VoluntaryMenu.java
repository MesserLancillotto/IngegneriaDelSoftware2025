package User;

import org.json.*;
import java.util.*;

import Client.Client;

public class VoluntaryMenu extends UserMenu
{
    private String voluntaryUserName;
    private ArrayList <String> associatedVisitType;

    public void initialize_menu_selection()
	{
		menuSelection.put(1, () -> view_associated_visit_type());
        menuSelection.put (2, () -> give_disponibility());
        //iterazione 4
        menuSelection.put (3, () -> view_confirmed_events());

        menuOptionList.add("Visualizza il tipo di visite a cui sei associato");
        menuOptionList.add("Fornisci i giorni in cui puoi lavorare");
        menuOptionList.add("Visualizza gli eventi confermati a cui devi fare da guida");
	}
   
    //COSTRUTTORE
    public VoluntaryMenu (String voluntaryUserName, ArrayList <String> associatedVisitType)
    {
        printCenteredTitle("MENU PRINCIPALE VOLONTARI");
        this.voluntaryUserName = voluntaryUserName;
        this.associatedVisitType = associatedVisitType;
        initialize_menu_selection();
        manage_options();
    }

    private void view_associated_visit_type ()
    {
        UserTui.stampSeparator();
        Set <String> visitType = new HashSet<>(associatedVisitType);
        StringBuilder msg = new StringBuilder();
        msg.append("Ecco i tipi di visita a cui sei associato (");
        msg.append(voluntaryUserName);
        msg.append (")");
        UserTui.stamp_list(msg.toString(), visitType);
        UserTui.stampSeparator();
    }

    private void give_disponibility()
    {
        DataManagerDisponibility date = new DataManagerDisponibility(2);
        boolean addAnotherDate;
        do
        {
            int disponibilityDay = date.getReferenceDay("Inserire il giorno dove puoi lavorare");
            if (disponibilityDay > 0)   // -1 indica errore durante l'acquisizione
            {
                int unixDate = (int)date.getUnixDate(disponibilityDay);
                String eventName = show_events_by_specific_day (unixDate);  //ottengo quale evento vuole

                if (!eventName.isEmpty()) //controllo che non abbia sbagliato troppo
                {
                    Client.getInstance().set_disponibility(eventName, unixDate);
                    String voluntaryDisponibilityResponse = Client.getInstance().make_server_request();
                    JSONObject dictionary = new JSONObject(voluntaryDisponibilityResponse);
                    UserTui.operationIsSuccessful (dictionary.getBoolean("querySuccesful"));
                } 
            }
            addAnotherDate = UserTui.getYesNoAnswer("Vuoi inserire un'altra data");
        }while (addAnotherDate);
    }

    private String show_events_by_specific_day (int date)
    {
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("withStartDate", date);
        filters.put ("eventName", "%");
        HashMap <Integer, String> validEventName = new HashMap<>();

        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        JSONArray eventsArray = new JSONArray(getEventResponse);

        System.out.println("Ecco gli eventi che si svolgono in quella data:");
        int loopCount = 1;
        for (int i = 0; i < getEventResponse.length(); i++)
        {
            JSONObject event = eventsArray.getJSONObject(i);
            int eventDate = event.getInt("startDate");   
            if (DataManager.isSameDay(eventDate, date))
                validEventName.put (loopCount, event.getString("eventName"));
        }
        String eventToChoose = UserTui.getChoiceFromMap("Scegli l'evento a cui vuoi dare la disponibilità:", validEventName);
        return eventToChoose;
    }

    public void view_confirmed_events()
    {
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        filters.put ("state", "%");
        filters.put ("startDate", "%");
        filters.put ("eventName", "%");
        filters.put ("description", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        JSONArray eventsArray = new JSONArray(getEventResponse);
        
        System.out.println ("\nEcco l'elenco delle visite confermate a cui devi fare da guida:");
        int cycleCount = 1;
        for (int i = 0; i < getEventResponse.length(); i++)
        {
            JSONObject event = eventsArray.getJSONObject(i);
            String tmpEventName = event.getString("eventName");
            String tmpDescription = event.getString("description");
            String tmpCity = event.getString("city");
            String tmpAddress = event.getString("address");
            int tmpStartDate = event.getInt("startDate");

            //IPOTETICO BISOGNA VEDERE SE VIENE IMPLEMENTATO COSÌ
            JSONArray voluntArray = event.getJSONArray("voluntarys"); // voluntary è da definire 
            ArrayList <String> voluntarysForThisEvent = JSONObjectMethod.jsonArrayConverterForComparisons(voluntArray);

            StateOfVisit visitState = StateOfVisit.fromString(event.getString("state"));
            
            if (visitState == StateOfVisit.CONFERMATA)
            {
                if (voluntarysForThisEvent.contains(voluntaryUserName.toUpperCase().trim()))
                {
                    String formattedDate = DataManager.fromUnixToNormal(tmpStartDate);
                    UserTui.stampEventInfo (cycleCount, tmpEventName, tmpDescription, tmpCity, tmpAddress, formattedDate, visitState);
                }
            }
            cycleCount++;
        }
    }
}
