package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class User 
{
    String userName;
	String cityOfResidence;
	int birthYear;
	String roleTitle;
	String userID;
    String password;
	
	Client client;
	
	
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
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		}
	}
	
	public void first_access (BufferedReader consoleIn)
	{
		try
		{
			set_new_username (consoleIn);
			set_new_password(consoleIn);
			System.out.printf("Inserisci la città di residenza: ");
			String cityOfResidence = consoleIn.readLine();
			
			System.out.printf("Inserisci l'anno di nascita: ");
			int birthYear = Integer.parseInt(consoleIn.readLine()); // da implementare controlli o interfaccia grafica per evitare errori
			
			roleTitle = "C"; // al momento Consumer unica classe attiva, da implementare le altre due nelle iterazioni successive
			
			
			
			Configurator configurator = new Configurator (userName, cityOfResidence, birthYear);
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		}
	}
	
	// metodo per creare nuovo username
	public String set_new_username (BufferedReader consoleIn)
	{
		String tmpUserName = "";
		try
		{
			do 
			{
				System.out.printf("Inserisci il nuovo username: ");
			    tmpUserName = consoleIn.readLine();
			} while (!client.makeServerRequest (tmpUserName) && tmpUserName != null && tmpUserName != " "); 
			// controllo che non esista già lo userName
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		}
		
		return tmpUserName;
		
	}
	// metodo per creare nuova password
	public String set_new_password (BufferedReader consoleIn)
	{
		String tmpPassword = " ";
		try
		{
			System.out.printf("Inserisci la nuova password: ");
			tmpPassword = consoleIn.readLine();
			client.makeServerRequest(userName); // gli passo userName e password per caricarli nel database
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		}
		
		return tmpPassword;
	}    
}
