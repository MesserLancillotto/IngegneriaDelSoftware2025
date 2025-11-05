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

        menuOptionList.add("Visualizza il tipo di visite a cui sei associato");
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
            int disponibilityDay = date.getReferenceDay("Inserire il giorno dove puoi lavorare", "Di quanti giorni è la disponibilità");
            if (disponibilityDay > 0)
            {
                int unixDate = (int)date.getUnixDate(disponibilityDay);
                //Client.getInstance().nome_metodo(unixDate, date.getEndDayOfClosure(), "ASSOCIAZIONE")
                String voluntaryDisponibilityResponse = Client.getInstance().make_server_request();
                JSONObject dictionary = new JSONObject(voluntaryDisponibilityResponse);
                UserTui.operationIsSuccessful (dictionary.getBoolean("querySuccesful"));
            }
            addAnotherDate = UserTui.getYesNoAnswer("Vuoi inserire un'altra data");
        }while (addAnotherDate);
    }
}
