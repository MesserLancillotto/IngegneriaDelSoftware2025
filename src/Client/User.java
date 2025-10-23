package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class User 
{
    String userName;
	String cityOfResidence;
	int birthYear;
	String roleTitle;
	String userID;
    String password;
	
	Client client;
    static HashMap <String, Runnable> roleTitleChooser = new HashMap <>();     //uso hashmap al posto di uno switch
    //in prova implementazione con supplier
    public static void initialize_role_title_chooser ()
    {
        roleTitleChooser.put ("C", () -> access_configurator());
        roleTitleChooser.put ("V", () -> access_configurator());
        roleTitleChooser.put ("T", () -> access_configurator());
    }
    
    public static void access_configurator()
    {

    }

    public static void access_voluntary ()
    {

    }

    public static void access_consumer ()
    {

    }

	public static void main (String[] args)
	{
		BufferedReader consoleIn = new BufferedReader (new InputStreamReader (System.in));
		
		login(consoleIn);
	}
	
	public static void login (BufferedReader consoleIn)
	{
		try
		{
			//deve diventare il metodo access()
			System.out.printf ("Inserisci username: ");
			String userName = consoleIn.readLine();
			System.out.printf ("Inserisci password: ");
			String password = consoleIn.readLine();

            //client.makeServerRequest();
            //controllo che siano giusti con una chiamata al server

            //da introdurre discriminante per verificare se è primo accesso o meno
            if (true)
            {
                first_access(consoleIn);    // se primo accesso
            }
            else
            {
                //client.makeServerRequest();
                //carico da server i dati che mi servono dell'utente
            }

            //qui andrebbe l'apertura di un thread per gestire più accessi 
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		}
	}
	
	public static void first_access (BufferedReader consoleIn)
	{
		try
		{
			String tmpUsername = set_new_username (consoleIn);
			String tmpPassword = set_new_password(consoleIn);
			System.out.printf("Inserisci la città di residenza: ");
			String cityOfResidence = consoleIn.readLine();
			
			System.out.printf("Inserisci l'anno di nascita: ");
			int birthYear = Integer.parseInt(consoleIn.readLine()); // da implementare controlli o interfaccia grafica per evitare errori
			
			String tmpRoleTitle = "C"; // al momento Consumer unica classe attiva, da implementare le altre due nelle iterazioni successive
			
			
			
			Configurator configurator = new Configurator (tmpUsername, cityOfResidence, birthYear, tmpPassword);
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		}
	}
	
	// metodo per creare nuovo username
	public static String set_new_username (BufferedReader consoleIn)
	{
		String tmpUserName = "";
        Boolean usernameIsNotAlreadyTaken = true;   
		try
		{
			do 
			{
				System.out.printf("Inserisci il nuovo username: ");
			    tmpUserName = consoleIn.readLine();
			} while (usernameIsNotAlreadyTaken && tmpUserName != null && tmpUserName.isBlank()); 
			// controllo che non esista già lo userName con una chiamata al server
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		}
		
		return tmpUserName;
		
	}
	// metodo per creare nuova password
	public static String set_new_password (BufferedReader consoleIn)
	{
		String tmpPassword = " ";
		try
		{
			System.out.printf("Inserisci la nuova password: ");
			tmpPassword = consoleIn.readLine();
			//client.makeServerRequest(userName); 
            // gli passo userName e password per caricarli nel database
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		}
		
		return tmpPassword;
	}    

    
}
