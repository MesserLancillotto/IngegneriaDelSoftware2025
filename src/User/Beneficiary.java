package User;

import java.util.*;
import org.json.*;
import Client.Client;

public class Beneficiary extends User
{
    private static final String GET_DATA_NAME = "Inserisci il nome: ";
    private static final String GET_DATA_SURNAME = "Inserisci il cognome: ";
    private static final String GET_DATA_PASSWORD = "Inserisci la password: ";
    private static final String GET_DATA_CITY = "Inserisci la tua città di residenza";
    private static final String GET_DATA_BIRTH_YEAR = "Inserisci l'anno di nascita";
    private static final String MSG_CREATE_NEW_USER = "CREAZIONE NUOVO UTENTE";
    private static final String ERROR_FAILED_USER_CREATION = "\nERRORE! Creazione nuovo utente non riuscita";
    private static final String ERROR_CONNECTION_SERVER = "\nErrore di connessione col server, riprova più tardi!\n";

    private static final int MIN_BIRTHYEAR = 1900;
    private static final int MAX_BIRTHYEAR = 2025;

    //COSTRUTTORE PRIMO ACCESSO
    public Beneficiary ()
    {
        if (create_new_account())
            new BeneficiaryMenu();
        else
            System.out.println (ERROR_FAILED_USER_CREATION);
    }
    //COSTRUTTORE BASE
    public Beneficiary (String username, String cityOfResidence, int birthYear, String password, 
                                String roleTitle, String organization, ArrayList <String> allowedVisitType)
    {
        this.userName = username;
        this.cityOfResidence = cityOfResidence;
        this.birthYear = birthYear;
        this.password = password;
        this.roleTitle = roleTitle;
        new BeneficiaryMenu ();
    }

    private boolean create_new_account ()
    {
        UserTui.stampTitle(MSG_CREATE_NEW_USER);
        StringBuilder userId = new StringBuilder();
        userId.append(UserTui.getString(GET_DATA_NAME));
        userId.append (" ");
        userId.append(UserTui.getString(GET_DATA_SURNAME));
        
        this.password = UserTui.getPasswordFromUser (GET_DATA_PASSWORD);
        this.cityOfResidence = UserTui.getString(GET_DATA_CITY);
		this.birthYear = UserTui.getInteger(GET_DATA_BIRTH_YEAR, MIN_BIRTHYEAR, MAX_BIRTHYEAR);

        Client.getInstance().set_new_user(userID.toString(), password, cityOfResidence, birthYear);
        String newUserAnswer = Client.getInstance().make_server_request();
        if (!newUserAnswer.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(newUserAnswer))
        {
            JSONObject dictionary = new JSONObject(newUserAnswer);
            boolean createNewAccountSuccess = dictionary.getBoolean("accessSuccesful");
            if (createNewAccountSuccess)
            {
                this.userName = dictionary.getString("userID");
                Client.getInstance().setUserID(userName);
                Client.getInstance().setUserPassword(this.password);
            }
            return UserTui.operationIsSuccessful (createNewAccountSuccess); // controlla che sia querySuccesful il campo
        }
        else
        {
            System.out.println (ERROR_CONNECTION_SERVER);
            return false;
        }
    }
}
