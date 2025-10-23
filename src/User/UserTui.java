package User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserTui 
{
    static BufferedReader consoleIn = new BufferedReader (new InputStreamReader (System.in));

    public static String getString (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ");
                String value = consoleIn.readLine();
                
                if (value != null && !value.isEmpty())
                    return value.trim();

                System.out.println ("Valore inserito non valido, riprova!\n");
            }
            catch (IOException errorDuringDigitation)
            {
                System.out.println ("\nErrore durante la digitazione" + errorDuringDigitation.getMessage());
                System.out.println ("Riprova");
            }
        }
    }

    public static int getInteger (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String input = consoleIn.readLine();
                if (input == null || input.isEmpty())
                    System.out.println ("Errore, valore inserito non valido");
                else
                    return Integer.parseInt(input);
             } 
            catch (IOException errorDuringDigitation)
            {
                System.out.println ("\nErrore durante la digitazione" + errorDuringDigitation.getMessage());
                System.out.println ("Riprova");
            }
        }
    }

    //metodo di acquisizione int con con range di validità
    public static int getInteger (String thingToSayToUser, int lowerBound, int upperBound)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String input = consoleIn.readLine();
                int tmpValue = Integer.parseInt(input);

                if ((input == null || input.isEmpty()) && tmpValue > lowerBound && tmpValue < upperBound)
                    System.out.println ("Errore, valore inserito non valido");
                else
                    return tmpValue;
             } 
            catch (IOException errorDuringDigitation)
            {
                System.out.println ("\nErrore durante la digitazione" + errorDuringDigitation.getMessage());
                System.out.println ("Riprova");
            }
        }
    }

    public static String getYesNoAnswer (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s ", thingToSayToUser);
                String answer = consoleIn.readLine();

                if (answer.toLowerCase().equals("si") || answer.toLowerCase().equals("no"))
                    return answer;
                System.out.println ("\nErrore, valore inserito non valido");
            }
            catch (IOException errorDuringDigitation)
            {
                System.out.println ("\nErrore durante la digitazione" + errorDuringDigitation.getMessage());
                System.out.println ("Riprova");
            }
        }
    }
}


