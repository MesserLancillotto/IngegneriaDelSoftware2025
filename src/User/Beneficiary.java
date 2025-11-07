package User;

import java.util.*;
import org.json.*;
import Client.Client;

public class Beneficiary extends User
{
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

    private void create_new_account ()
    {
        String userName = UserTui.getString("Inserisci username");      //Glielo generiamo noi????
        StringBuilder userId = new StringBuilder();
        userId.append(UserTui.getStringNoTrim("Inserisci il nome: "));
        userId.append (" ");
        userId.append(UserTui.getStringNoTrim("Inserisci il cognome: "));
        
        this.password = UserTui.getPasswordFromUser ("Inserisci la nuova password");
        this.cityOfResidence = UserTui.getString("Inserisci la tua città di residenza");
		this.birthYear = UserTui.getInteger("Inserisci l'anno di nascita", 1900, 2025);

        //Client.getInstance().nome_metodo(gli passo gli attributi);
        String newUserAnswer = Client.getInstance().make_server_request();
        JSONObject dictionary = new JSONObject(newUserAnswer);
        UserTui.operationIsSuccessful (dictionary.getBoolean("querySuccesful")); // controlla che sia querySuccesful il campo
    }
}
