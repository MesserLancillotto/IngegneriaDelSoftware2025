package User;

import java.util.*;
import org.json.*;
import Client.Client;

public class Beneficiary extends User
{
    private static final String GET_DATA_NAME = "Inserisci il nome: ";
    private static final String GET_DATA_SURNAME = "Inserisci il cognome: ";
    private static final String GET_DATA_PASSWORD = "Inserisci la nuova password: ";
    private static final String GET_DATA_CITY = "Inserisci la tua città di residenza";
    private static final String GET_DATA_BIRTH_YEAR = "Inserisci l'anno di nascita";
    private static final String ERROR_CONNECTION_SERVER = "\nErrore di connessione col server, riprova più tardi!\n";
    //COSTRUTTORE PRIMO ACCESSO
    public Beneficiary ()
    {
        create_new_account();
        new BeneficiaryMenu();
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
        String userName = UserTui.getString("Inserisci username");      //Glielo generiamo noi????
        StringBuilder userId = new StringBuilder();
        userId.append(UserTui.getStringNoTrim(GET_DATA_NAME));
        userId.append (" ");
        userId.append(UserTui.getStringNoTrim(GET_DATA_SURNAME));
        
        this.password = UserTui.getPasswordFromUser (GET_DATA_PASSWORD);
        this.cityOfResidence = UserTui.getString(GET_DATA_CITY);
		this.birthYear = UserTui.getInteger(GET_DATA_BIRTH_YEAR, 1900, 2025);

        //Client.getInstance().nome_metodo(gli passo gli attributi);
        String newUserAnswer = Client.getInstance().make_server_request();
        if (!newUserAnswer.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(newUserAnswer))
        {
            JSONObject dictionary = new JSONObject(newUserAnswer);
            return UserTui.operationIsSuccessful (dictionary.getBoolean("querySuccesful")); // controlla che sia querySuccesful il campo
        }
        else
        {
            System.out.println (ERROR_CONNECTION_SERVER);
            return false;
        }
    }
}
