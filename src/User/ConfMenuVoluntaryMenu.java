package User;

import java.util.*;
import org.json.*;
import Client.Client;

public class ConfMenuVoluntaryMenu extends UserMenu
{
    private String organization;
    public void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> view_voluntary_list());
        //iterazione 3
        menuSelection.put(2, () -> remove_voluntary());
        menuSelection.put(3, () -> close_disponibility_collection());
        menuSelection.put(4, () -> open_disponibility_collection());
        menuSelection.put(5, () -> add_voluntary_to_existing_visit());

        menuOptionList.add("Visualizza l'elenco volontari");
        menuOptionList.add("Rimuovi un volontario");
        menuOptionList.add("Chiudi la raccolta delle disponibilità dei volontari");
        menuOptionList.add("Apri la raccolta delle disponibilità dei volontari");
        menuOptionList.add("Aggiungi un volontario a una visita esistente");
    }

    public ConfMenuVoluntaryMenu (String organization)
    {
        printCenteredTitle("MENU GESTIONE VOLONTARI");
        this.organization = organization;
        initialize_menu_selection();
        manage_options();  
    }

    //DA SISTEMARE
    public void view_voluntary_list()
    {
        Client.getInstance().get_voluntaries_for_visit("");
        String getVoluntariesResponse = Client.getInstance().make_server_request();
        Set <String> voluntaryList; //= new HashSet <>(JSONObjectMethod.extractArray(getVoluntariesResponse, "userID"));
        voluntaryList = new HashSet<>();
        UserTui.stamp_list("Ecco l'elenco dei volontari iscritti:", voluntaryList);
    }

    public void remove_voluntary()
    {
        //DA IMPLEMENTARE
    }

    public void close_disponibility_collection()
    {
        boolean makeServerCall = UserTui.getYesNoAnswer("Sei sicuro di voler chiudere la raccolta delle disponibilità dei volontari");
        if (makeServerCall)
        {
            //Client.getInstance().close_voluntary_disponibility_collection(organization);
            String closeVoluntaryDisponibilityCollectionResponse = Client.getInstance().make_server_request();
            JSONObject dictionary = new JSONObject(closeVoluntaryDisponibilityCollectionResponse);
            UserTui.operationIsSuccessful (dictionary.getBoolean("querySuccesful"));    // controlla che si effettivamente "querySuccesful" sia il campo giusto
        }
    }

    public void open_disponibility_collection()
    {
        boolean makeServerCall = UserTui.getYesNoAnswer("Sei sicuro di voler aprire la raccolta delle disponibilità dei volontari");
        if (makeServerCall)
        {
            //Client.getInstance().open_voluntary_disponibility_collection(organization);
            String openVoluntaryDisponibilityCollectionResponse = Client.getInstance().make_server_request();
            JSONObject dictionary = new JSONObject(openVoluntaryDisponibilityCollectionResponse);
            UserTui.operationIsSuccessful (dictionary.getBoolean("querySuccesful"));    // controlla che si effettivamente "querySuccesful" sia il campo giusto
        }
    }   

    public void add_voluntary_to_existing_visit()
    {
        //DA IMPLEMENTARE
    }
}
