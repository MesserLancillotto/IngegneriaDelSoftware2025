public abstract class User 
{
    String userName;
	String cityOfResidence;
	int birthYear;
	String roleTitle;
	String userID;
    String password;

      // metodo per creare nuovo username
	public static String set_new_username (String tmpRoleTitle)
	{
		StringBuilder tmpUsername = new StringBuilder();
        String userInput;
        Boolean usernameIsNotAlreadyTaken = false;
        
        tmpUsername.append(tmpRoleTitle);
		
		do 
		{
			userInput = tmpRoleTitle+UserTui.getString ("Inserisci il nuovo username");	//usare StringBuilder
			//lo user decide 
		}while (usernameIsNotAlreadyTaken);
		//chiamata al server per controllare che non esista già

        tmpUsername.append(userInput);

		//client.makeServerRequest();
		//mando al server il nuovo username da salvare
		return tmpUsername.toString();
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
