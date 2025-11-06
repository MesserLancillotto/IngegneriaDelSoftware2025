package User;
import java.util.*;
import Client.Client;

import org.json.*;

public class ConfiguratorMenu extends UserMenu
{
    String organization; 

    public void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> handle_voluntaries());
        menuSelection.put(2, () -> handle_places());
        menuSelection.put(3, () -> handle_generic_stuff());  
        menuSelection.put(4, () -> view_visit_state());    

        menuOptionList.add("Apri menu gestione volontari");
        menuOptionList.add("Apri menu gestione luoghi visitabili");
        menuOptionList.add("Apri menu gestione opzioni generiche");
        menuOptionList.add("Visualizza le visite in stato di visita");	
    }

    //COSTRUTTORE
    public ConfiguratorMenu (String organization)
    {
        printCenteredTitle("MENU PRINCIPALE CONFIGURATORE");
        this.organization = organization;   
        initialize_menu_selection();
        manage_options();
    }

    public ConfiguratorMenu (Boolean isFirstAccess, String organization)
    {
        new ConfMenuPlaceMenu(isFirstAccess, organization); 
    }

    public void handle_voluntaries ()
    {
        new ConfMenuVoluntaryMenu (organization);
    }
	
    public void handle_places ()
    {
        new ConfMenuPlaceMenu (organization);
    }

    public void handle_generic_stuff()
    {
        new ConfMenuGenericOptions (organization);
    }

    private void view_visit_state ()
    {
        ArrayList <Visit> visitList = new ArrayList<>();
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        filters.put ("state", "%");
        filters.put ("startDate", "%");
        filters.put ("eventName", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        JSONArray eventsArray = new JSONArray(getEventResponse);

        for (int i = 0; i < getEventResponse.length(); i++)
        {
            JSONObject event = eventsArray.getJSONObject(i);
            String tmpEventName = event.getString("eventName");
            String tmpCity = event.getString("city");
            String tmpAddress = event.getString("address");
            String tmpState = event.getString("state");
            int tmpStartDate = event.getInt("startDate");

            visitList.add(new Visit (tmpEventName, tmpCity, tmpAddress, tmpState, tmpStartDate));
        }

        System.out.println ("\nEcco l'elenco delle visite attualmente nello stato di proposto/completato/confermato/cancellato/effettuato");
        for (Visit v: visitList)
        {
            if (v.getVisitState() == StateOfVisit.CANCELLATA || v.getVisitState() == StateOfVisit.PROPOSTA ||
                        v.getVisitState() == StateOfVisit.COMPLETA || v.getVisitState() == StateOfVisit.CONFERMATA || v.getVisitState() == StateOfVisit.EFFETTUATA)
            {
                System.out.printf ("La visita %s, che si tiene a %s il %s ed è nello stato %s\n", v.getEventName(), v.getPlace(), v.getStartDay(),v.getVisitState().toString());
            }
        }
    }
}
