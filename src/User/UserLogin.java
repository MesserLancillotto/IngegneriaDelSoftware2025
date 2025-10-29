package User;
import java.util.HashMap;

import Client.Client;

public class UserLogin 
{   
    private static HashMap <String, UserCreator> userFactory = new HashMap <>();
	private static HashMap <String, UserFirstAccessCreator> userFirstAccessFactory = new HashMap <>();

    private static void initialize_user_factory ()
    {
        userFactory.put ("C", (username, cityOfResidence, birthYear, password) -> new Configurator (username, cityOfResidence, birthYear, password));  

		userFirstAccessFactory.put ("C", (tmpUsernName, tmpPassword) -> new Configurator (tmpUsernName, tmpPassword));
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

            String logRequest = Client.loginRequest (userName, password);
            String loginResult = Client.makeServerRequest (Client.getServerAddr(), Client.getPort(), logRequest);

            loginSuccessfull = JSONObject.extractBoolean(loginResult, "loginSuccessful");
            
            if (loginSuccessfull)
            {
                if (userName.substring(0,1).equals ("T"))
                {
                    userFirstAccessFactory.get("C").create (userName, password);
                }
                else 
                {
                    //client.makeServerRequest();
                    //carico da server i dati che mi servono dell'utente
                    String cityOfResidence = "";
                    int birthYear = 0;
                    userFactory.get(userName.substring(0,1)).create (userName, cityOfResidence, birthYear, password);
                }
            }
        }while (!loginSuccessfull);
	}    

}
