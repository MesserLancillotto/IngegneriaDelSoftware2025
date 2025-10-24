import java.util.*;
import Client.Client;

public class User 
{
    String userName;
	String cityOfResidence;
	int birthYear;
	String roleTitle;
	String userID;
    String password;
	
	Client client;
    
    private static HashMap <String, UserCreator> userFactory = new HashMap <>();
	private static HashMap <String, UserFirtsAccessCreator> userFirstAccessFactory = new HashMap <>();

	private static final String TEMPORARY_PASSWORD = "password";
	private static final String TEMPORARY_CONFIGURATOR_ID = "configurator";

    private static void initialize_user_factory ()
    {
        userFactory.put ("C", (username, cityOfResidence, birthYear, password) -> new Configurator (username, cityOfResidence, birthYear, password));  

		userFirstAccessFactory.put ("C", (username, password) -> new Configurator (username,password));
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
			first_access("C_");    // se primo accesso
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
	
	private static void first_access (String tmpRoleTitle)
	{
		String tmpUsername = set_new_username (tmpRoleTitle);
		String tmpPassword = set_new_password();
		// al momento Consumer unica classe attiva, da implementare le altre due nelle iterazioni successive
		
		userFirstAccessFactory.get(tmpUsername.substring(0,1)).create (tmpUsername, tmpPassword);
	}
	
	// metodo per creare nuovo username
	public static String set_new_username (String tmpRoleTitle)
	{
		String tmpUsername;
        Boolean usernameIsNotAlreadyTaken = false;   
		
		do 
		{
			tmpUsername = tmpRoleTitle+UserTui.getString ("Inserisci il nuovo username");	//usare StringBuilder
			//lo user decide 
		}while (usernameIsNotAlreadyTaken);
		//chiamata al server per controllare che non esista già

		//client.makeServerRequest();
		//mando al server il nuovo username da salvare
		return tmpUsername;
	}
	// metodo per creare nuova password
	public static String set_new_password ()
	{
		String tmpPassword = UserTui.getString ("Inserisci la nuova password");
		//client.makeServerRequest();
		//mando al server la nuova password da salvare
		return tmpPassword;
	}    

}
