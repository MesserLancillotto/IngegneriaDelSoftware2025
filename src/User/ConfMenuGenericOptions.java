package User;

import Client.Client;
import org.json.*;

public class ConfMenuGenericOptions extends UserMenu
{
    public void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> modify_max_number_per_subscription());
        menuSelection.put(2, () -> manage_disponibilty_dates());  

        menuOptionList.add("Segna date precluse alle visite");
        menuOptionList.add("Modifica il numero massimo di persone iscrivibili a un'iniziativa da parte di un fruitore");
    }
    
    public ConfMenuGenericOptions ()
    {
        printCenteredTitle("MENU CONFIGURATORE: OPZIONI GENERICHE");        //titolo temporaneo
        initialize_menu_selection();
        manage_options();  
    }

    public void modify_max_number_per_subscription()
    {
        int newMaxNumber = UserTui.getInteger("\nDefinire il nuovo numero massimo di persone che un fruitore può iscrivere in una volta sola", 0, 100);
        JSONObjectCreator.setMaxPeopleForSubscription(newMaxNumber);
    }

     public void manage_disponibilty_dates ()
    {
        boolean addAnotherDate;
        DataManagerDisponibility date = new DataManagerDisponibility(3);
        do
        {
            int unaviableDay = date.getReferenceDay("Inserire il giorno precluso alle visite", "Di quanti giorni è la chiusura");
            if (unaviableDay > 0)
            {
                int unixDate = (int)date.getUnixDate(unaviableDay);
                Client.getInstance().set_closed_days(unixDate, date.getEndDayOfClosure(), "ASSOCIAZIONE");  // definisci ASSOCIAZIONE
                String closedDaysReply = Client.getInstance().make_server_request();
                JSONObject dictionary = new JSONObject(closedDaysReply);
                UserTui.operationIsSuccessful(dictionary.getBoolean("querySuccesful"));
            }
            addAnotherDate = UserTui.getYesNoAnswer("Vuoi inserire un'altra data");
        }while (addAnotherDate);
    }

}
