package User;

import org.json.*;

import java.lang.Thread.State;
import java.util.*;

import Client.Client;

public class BeneficiaryMenu extends UserMenu
{
     public void initialize_menu_selection()
	{
		menuSelection.put(1, () -> view_all_visits_list()); // visite allo stato proposto/confermato/cancellato
        menuSelection.put(2, () -> register_for_visit()); // registrazione ad una visita
        menuSelection.put(3, () -> view_registered_visits()); // visualizzazione visite a cui si è registrati
        menuSelection.put(4, () -> cancel_registration_for_visit()); // cancellazione registrazione ad una visita

        menuOptionList.add("Visualizza il tipo di visite a cui sei associato");
        menuOptionList.add("Registrati per una visita");
        menuOptionList.add("Visualizza le visite a cui sei registrato");
        menuOptionList.add("Cancella la registrazione ad una visita");
	}

    public BeneficiaryMenu ()
    {
        printCenteredTitle("MENU PRINCIPALE");
        initialize_menu_selection();
        manage_options();
    }

    public void view_all_visits_list()
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
        
        System.out.println ("\nEcco l'elenco delle visite attualmente nello stato di proposto/confermato/cancellato");
        int cycleCount = 1;
        for (int i = 0; i < getEventResponse.length(); i++)
        {
            JSONObject event = eventsArray.getJSONObject(i);
            String tmpEventName = event.getString("eventName");
            String tmpDescription = event.getString("description");
            String tmpCity = event.getString("city");
            String tmpAddress = event.getString("address");
            int tmpStartDate = event.getInt("startDate");
            StateOfVisit visitState = StateOfVisit.fromString(event.getString("state"));
            
            if (visitState == StateOfVisit.CANCELLATA || visitState == StateOfVisit.PROPOSTA || visitState == StateOfVisit.CONFERMATA)
            {
                String formattedDate = DataManager.fromUnixToNormal(tmpStartDate);
        
                // Mappa gli stati a icone e colori
                String stateIcon = UserTui.getStateIcon(visitState);
                
                System.out.println("\n");
                UserTui.stampSeparator();
                System.out.printf("🏷️  VISITA #%d\n", cycleCount);
                System.out.printf("📌 %s\n", tmpEventName);
                System.out.printf("📝 %s\n", tmpDescription);
                System.out.printf("📍 %s - %s\n", tmpCity, tmpAddress);
                System.out.printf("📅 %s\n", formattedDate);
                System.out.printf("%s %s\n", stateIcon, UserTui.getStateDescription(visitState));
                UserTui.stampSeparator();
            }
            cycleCount++;
            UserTui.stampSeparator();
        }
    }

    public void register_for_visit()
    {
        //da implementare
    }

    public void view_registered_visits()
    {
        //da implementare
    }

    public void cancel_registration_for_visit()
    {
        //da implementare
    }
}
