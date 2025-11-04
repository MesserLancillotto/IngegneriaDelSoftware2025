package User;

import Client.Client;

public class VoluntaryMenu implements UserMenu
{
    private String voluntaryUserName;

    public void initialize_menu_selection()
	{
		menuSelection.put(1, () -> view_associated_visit_type());
        menuSelection.put (2, () -> give_disponibility());

        menuOptionList.add("Visualizza il tipo di visite a cui sei associato");
	}
    public void visualize_options ()
	{
        int optionCount = 1;
        for (String options : menuOptionList)
        {
            System.out.println (optionCount+options);
        }
        menuSelection.get(UserTui.getInteger("Cosa vuoi fare", 0, optionCount+1))
                            .run();
	}

    public void manage_options ()
	{
        boolean keepUsingConfiguratorMenu;
        do
        {
            visualize_options();

            System.out.printf ("\nVuoi fare altro: ");
            keepUsingConfiguratorMenu = UserTui.getYesNoAnswer("\nVuoi fare altro");
        }while (keepUsingConfiguratorMenu);
	}
    //COSTRUTTORE
    public VoluntaryMenu (String voluntaryUserName)
    {
        this.voluntaryUserName = voluntaryUserName;
        initialize_menu_selection();
        manage_options();
    }

    private void view_associated_visit_type ()
    {
        //Client.getInstance().get_event(voluntaryUserName);
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
                JSONObjectMethod.confirmRequest (voluntaryDisponibilityResponse, "querySuccesful");
            }
            addAnotherDate = UserTui.getYesNoAnswer("Vuoi inserire un'altra data");
        }while (addAnotherDate);
    }
}
