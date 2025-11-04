package User;
import Client.Client;
import org.json.*;
public abstract class User 
{
    String userName;
	String cityOfResidence;
	int birthYear;
	String roleTitle;
	String userID;
    String password;
	String organization;

	// metodo per creare nuova password
	public boolean set_new_password ()
	{
		String tmpPassword = UserTui.getPasswordFromUser ("Inserisci la nuova password");
		Client.getInstance().edit_password(tmpPassword);
		String replyChangePassword = Client.getInstance().make_server_request();

		JSONObject dictionary = new JSONObject(replyChangePassword);
		boolean changePasswordSuccessfull = dictionary.getBoolean("passwordChangeSuccessful");

		if (changePasswordSuccessfull)
		{
			System.out.println ("La password è stata cambiata!");
			Client.getInstance().setUserPassword(tmpPassword);
			return true;
		}
		else
		{
			System.out.println ("Errore, non è stato possibile cambiare la password");
			return false;
		}
	}
}
