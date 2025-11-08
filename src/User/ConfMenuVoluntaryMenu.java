package User;

import java.util.*;
import org.json.*;
import Client.Client;

public class ConfMenuVoluntaryMenu extends UserMenu
{
    private static final String MENU_TITLE = "MENU GESTIONE VOLONTARI";
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
        printCenteredTitle(MENU_TITLE);
        this.organization = organization;
        initialize_menu_selection();
        UserTui.stampSeparator();
        manage_options(MENU_TITLE);  
    }

    public void view_voluntary_list()   // manca chiamata al server + da sistemare
    {
        Client.getInstance().get_voluntaries_for_visit("");
        String getVoluntariesResponse = Client.getInstance().make_server_request();
        Set <String> voluntaryList; //= new HashSet <>(JSONObjectMethod.extractArray(getVoluntariesResponse, "userID"));
        voluntaryList = new HashSet<>();
        UserTui.stamp_list("Ecco l'elenco dei volontari iscritti:", voluntaryList);
    }

    public void remove_voluntary()      // manca chiamata al server
    {
        String voluntaryToRemove = UserTui.getString("Inserire lo username del volontario da rimuovere");
        //Client.getInstance().remove_voluntary();
        String removeVoluntaryResponse = Client.getInstance().make_server_request();
        if (!removeVoluntaryResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(removeVoluntaryResponse))
        {
            JSONObject dictionary = new JSONObject(removeVoluntaryResponse);
            UserTui.operationIsSuccessful(dictionary.getBoolean("querySuccesful")); //CONTROLLA la key word
        }
    }

    public void close_disponibility_collection()    // manca chiamata al server
    {
        boolean makeServerCall = UserTui.getYesNoAnswer("Sei sicuro di voler chiudere la raccolta delle disponibilità dei volontari");
        if (makeServerCall)
        {
            //Client.getInstance().close_voluntary_disponibility_collection(organization);
            String closeVoluntaryDisponibilityCollectionResponse = Client.getInstance().make_server_request();
            if (!closeVoluntaryDisponibilityCollectionResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(closeVoluntaryDisponibilityCollectionResponse))
            {
                JSONObject dictionary = new JSONObject(closeVoluntaryDisponibilityCollectionResponse);
                UserTui.operationIsSuccessful (dictionary.getBoolean("querySuccesful"));    // controlla key word
            }
        }
    }

    public void open_disponibility_collection()     // manca chiamata al server
    {
        boolean makeServerCall = UserTui.getYesNoAnswer("Sei sicuro di voler aprire la raccolta delle disponibilità dei volontari");
        if (makeServerCall)
        {
            //Client.getInstance().open_voluntary_disponibility_collection(organization);
            String openVoluntaryDisponibilityCollectionResponse = Client.getInstance().make_server_request();
            if (!openVoluntaryDisponibilityCollectionResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(openVoluntaryDisponibilityCollectionResponse))
            {
                JSONObject dictionary = new JSONObject(openVoluntaryDisponibilityCollectionResponse);
                UserTui.operationIsSuccessful (dictionary.getBoolean("querySuccesful"));    // controlla key word
            }
        }
    }   

    public void add_voluntary_to_existing_visit()
    {
        //DA IMPLEMENTARE
    }
}
