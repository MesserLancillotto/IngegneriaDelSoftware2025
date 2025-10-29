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
	public void set_new_password ()
	{
		String tmpPassword = UserTui.getPasswordFromUser ("Inserisci la nuova password");
		String requestChangePassword = Client.passwordChange(userName, password, tmpPassword);
		String replyChangePassword = Client.makeServerRequest(Client.getServerAddr(), Client.getPort(), requestChangePassword);
		boolean changePasswordSuccessfull = JSONObject.extractBoolean(replyChangePassword, "passwordChangeSuccessful");

		if (changePasswordSuccessfull)
		{
			System.out.println ("La password è stata cambiata!");
			this.password = tmpPassword;
		}
		else
			System.out.println ("Errore, non è stato possibile cambiare la password");
		
	}
}
