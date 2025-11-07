package User;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class UserMenu 
{
    HashMap <Integer, Runnable> menuSelection = new HashMap <>();
    ArrayList <String> menuOptionList = new ArrayList<>();

    abstract void initialize_menu_selection ();
    
    public void visualize_options ()
	{
        int optionCount = 1;
        for (String options : menuOptionList)
        {
            System.out.println (optionCount+"."+options);
            optionCount++;
        }
        menuSelection.get(UserTui.getInteger("Cosa vuoi fare (Inserisci il numero dell'opzione)", 0, optionCount+1))
                            .run();
	}

    public static void printCenteredTitle(String title) 
    {
        int totalWidth = 50;
        int padding = (totalWidth - title.length() - 2) / 2;
        
        String border = "✽".repeat(totalWidth);
        String paddingStr = " ".repeat(padding);
        
        System.out.println("\n" + border);
        System.out.println("✽" + paddingStr + " " + title + " " + paddingStr + "✽");
        System.out.println(border + "\n");
    }
    
    public void manage_options ()
	{
        boolean keepUsingConfiguratorMenu;
        do
        {
            UserTui.stampSeparator();
            visualize_options();
            keepUsingConfiguratorMenu = UserTui.getYesNoAnswer("\nVuoi fare altro");
            UserTui.stampSeparator();
        }while (keepUsingConfiguratorMenu);
	}

}
