import java.util.HashMap;

import Client.Client;

public class UserLogin 
{   
    private static HashMap <String, UserCreator> userFactory = new HashMap <>();
	private static HashMap <String, UserFirtsAccessCreator> userFirstAccessFactory = new HashMap <>();

	private static final String TEMPORARY_PASSWORD = "password";
	private static final String TEMPORARY_CONFIGURATOR_ID = "configurator";

    private static void initialize_user_factory ()
    {
        userFactory.put ("C", (username, cityOfResidence, birthYear, password) -> new Configurator (username, cityOfResidence, birthYear, password));  

		userFirstAccessFactory.put ("C", () -> new Configurator ());
    }

	public static void main (String[] args)
	{
        initialize_user_factory();
		login();
	}
	
	private static void login ()
	{
		String userName = UserTui.getString("Inserisci username");
		String password = UserTui.getString("Inserisci password");

		//client.makeServerRequest();
		//controllo che siano giusti con una chiamata al server, da aggiungere ciclo finché l'utente non fa l'accesso

		if (userName.equals(TEMPORARY_CONFIGURATOR_ID) && password.equals(TEMPORARY_PASSWORD))
		{
			userFirstAccessFactory.get("C").create ();
		}
		else
		{
			//client.makeServerRequest();
			//carico da server i dati che mi servono dell'utente
            String tmpUsername = "";
            String cityOfResidence = "";
            int birthYear = 0;
            String tmpPassword = "";
            userFactory.get(tmpUsername.substring(0,1)).create (tmpUsername, cityOfResidence, birthYear, tmpPassword);
		}

		//qui andrebbe l'apertura di un thread per gestire più accessi 
	}    

}
