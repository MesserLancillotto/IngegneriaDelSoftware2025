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
        Set <String> visitType = new HashSet<>(associatedVisitType);
        StringBuilder msg = new StringBuilder();
        msg.append("Ecco i tipi di visita a cui sei associato (");
        msg.append(voluntaryUserName);
        msg.append (")");
        UserTui.stamp_list(msg.toString(), visitType);
    }

    private void give_disponibility()
    {
        DataManagerDisponibility date = new DataManagerDisponibility(2);
        boolean addAnotherDate;
        do
        {
            int disponibilityDay = date.getReferenceDay("Inserire il giorno dove puoi lavorare");
            if (disponibilityDay > 0)
            {
                int unixDate = (int)date.getUnixDate(disponibilityDay);
                String eventName = ""; //da implementare la scelta dell'evento
                Client.getInstance().set_disponibility(eventName, unixDate);
                String voluntaryDisponibilityResponse = Client.getInstance().make_server_request();
                JSONObject dictionary = new JSONObject(voluntaryDisponibilityResponse);
                UserTui.operationIsSuccessful (dictionary.getBoolean("querySuccesful"));
            }
            addAnotherDate = UserTui.getYesNoAnswer("Vuoi inserire un'altra data");
        }while (addAnotherDate);
    }



    private void show_events_by_specific_day (int date)
    {
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("withStartDate", date);
        filters.put ("eventName", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        JSONArray eventsArray = new JSONArray(getEventResponse);

        System.out.println("Ecco gli eventi in cui sei coinvolto in quella data:");
        for (int i = 0; i < getEventResponse.length(); i++)
        {
            JSONObject event = eventsArray.getJSONObject(i);
            
        }
    }

    public void view_confirmed_events()
    {
        //DA IMPLEMENTARE
    }
}
