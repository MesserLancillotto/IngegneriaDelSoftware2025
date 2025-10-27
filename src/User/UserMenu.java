import java.util.*;

public interface UserMenu 
{
    HashMap <Integer, Runnable> menuSelection = new HashMap <>();
    ArrayList <String> menuOptionList = new ArrayList<>();

    void initialize_menu_selection ();
    void visualize_options ();
}
