package User;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.*;
import Client.Client;

public class UserLogin 
{   
    private static HashMap <String, UserCreator> userFactory = new HashMap <>();
	private static HashMap <String, UserFirstAccessCreator> userFirstAccessFactory = new HashMap <>();

    private static final String TEMPORARY_SYMBOL = "T";
    private static final String CONFIGURATOR_SYMBOL = "C";
    private static final String VOLUNTARY_SYMBOL = "V";

    private static void initialize_user_factory ()
    {
        userFactory.put (CONFIGURATOR_SYMBOL, (username, cityOfResidence, birthYear, password, roleTitle, organization, allowedVisitType) -> new Configurator (username, cityOfResidence, birthYear, password, roleTitle, organization, allowedVisitType));  
        userFactory.put (VOLUNTARY_SYMBOL, (username, cityOfResidence, birthYear, password, roleTitle, organization, allowedVisitType) -> new Voluntary (username, cityOfResidence, birthYear, password, roleTitle, organization, allowedVisitType));  
        
		userFirstAccessFactory.put (CONFIGURATOR_SYMBOL, (tmpUsernName, tmpPassword, roleTitle) -> new Configurator (tmpUsernName, tmpPassword, roleTitle));
        userFirstAccessFactory.put (VOLUNTARY_SYMBOL, (tmpUsernName, tmpPassword, roleTitle) -> new Voluntary (tmpUsernName, tmpPassword, roleTitle));
    }

	public static void main (String[] args)
	{
        initialize_user_factory();
		login();
	}
	
	private static void login ()
	{
        boolean loginSuccessfull;
        do
        {
            String userName = UserTui.getString("Inserisci username");
            String password = UserTui.getString("Inserisci password");
            Client.getInstance().setUserID (userName);
            Client.getInstance().setUserPassword (password);

            Client.getInstance().get_user_data(userName);
            String loginResult = Client.getInstance().make_server_request();
            JSONObject dictionary = new JSONObject(loginResult);

            loginSuccessfull = dictionary.getBoolean("loginSuccessful");
            
            if (loginSuccessfull)
            {
                boolean passwordNeedsToBeChanged = dictionary.getBoolean("passwordChangeDue");
                String roleTitle = dictionary.getString("role");
                if (passwordNeedsToBeChanged)
                {
                    if (userName.substring(0,1).equals (TEMPORARY_SYMBOL))
                        userFirstAccessFactory.get(CONFIGURATOR_SYMBOL).create (userName, password, roleTitle);
                    else
                        userFirstAccessFactory.get(userName.substring(0,1)).create(userName, password, roleTitle);
                }
                else 
                {
                    String cityOfResidence = dictionary.getString("cityOfResidence");
                    int birthYear = dictionary.getInt("birthYear");
                    String organization = dictionary.getString("organization");
                    ArrayList <String> allowedVisitType = JSONObjectMethod.jsonArrayConverter(dictionary.getJSONArray("allowedVisitType"));
                    userFactory.get(userName.substring(0,1)).create (userName, cityOfResidence, birthYear, password, roleTitle, organization, allowedVisitType);
                }
            }
        }while (!loginSuccessfull);
	}    

}
