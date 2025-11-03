package User;
import Client.Client;
public abstract class User 
{
    String userName;
	String cityOfResidence;
	int birthYear;
	String roleTitle;
	String userID;
    String password;

	// metodo per creare nuova password
	public boolean set_new_password ()
	{
		String tmpPassword = UserTui.getPasswordFromUser ("Inserisci la nuova password");
		Client.getInstance().edit_password(tmpPassword);
		String replyChangePassword = Client.getInstance().make_server_request();
		boolean changePasswordSuccessfull = JSONObject.extractBoolean(replyChangePassword, "passwordChangeSuccessful");

		if (changePasswordSuccessfull)
		{
			System.out.println ("La password è stata cambiata!");
			Client.setUserPassword(tmpPassword);
			return true;
		}
		else
		{
			System.out.println ("Errore, non è stato possibile cambiare la password");
			return false;
		}
		
	}
}
