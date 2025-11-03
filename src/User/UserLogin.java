package User;
import java.util.HashMap;

import Client.Client;

public class UserLogin 
{   
    private static HashMap <String, UserCreator> userFactory = new HashMap <>();
	private static HashMap <String, UserFirstAccessCreator> userFirstAccessFactory = new HashMap <>();

    private static void initialize_user_factory ()
    {
        userFactory.put ("C", (username, cityOfResidence, birthYear, password, roleTitle) -> new Configurator (username, cityOfResidence, birthYear, password, roleTitle));  
        userFactory.put ("V", (username, cityOfResidence, birthYear, password, roleTitle) -> new Voluntary (username, cityOfResidence, birthYear, password, roleTitle));  
		userFirstAccessFactory.put ("C", (tmpUsernName, tmpPassword, roleTitle) -> new Configurator (tmpUsernName, tmpPassword, roleTitle));
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
            Client.setUserID (userName);
            Client.setUserPassword (password);
            Client.getInstance().get_user_data(userName);
            String loginResult = Client.getInstance().make_server_request();

            loginSuccessfull = JSONObject.extractBoolean(loginResult, "loginSuccessful");
            
            if (loginSuccessfull)
            {
                String roleTitle =JSONObject.getJsonValue(loginResult, "role");
                if (userName.substring(0,1).equals ("T"))
                {
                    userFirstAccessFactory.get("C").create (userName, password, roleTitle);
                }
                else 
                {
                    String cityOfResidence = JSONObject.getJsonValue(loginResult, "cityOfResidence");
                    int birthYear = Integer.parseInt(JSONObject.getJsonValue(loginResult, "birthYear"));
                    userFactory.get(userName.substring(0,1)).create (userName, cityOfResidence, birthYear, password, roleTitle);
                }
            }
        }while (!loginSuccessfull);
	}    

}
