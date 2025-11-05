package User;

import java.util.*;
import org.json.*;
import Client.Client;

public class ConfMenuPlaceMenu extends UserMenu
{
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

    public ConfMenuPlaceMenu ()
    {
        printCenteredTitle("MENU GESTIONE POSTI VISITABILI");
        initialize_menu_selection();
        manage_options();
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
        //DA IMPLEMENTARE
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
