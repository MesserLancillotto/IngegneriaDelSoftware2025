package User;
import java.io.*;
import java.util.*;

public class UserTui 
{
    static BufferedReader consoleIn = new BufferedReader (new InputStreamReader (System.in));

    public static String getPasswordFromUser (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.println ("La password necessita di almeno una maiuscola e di un numero");
                System.out.printf ("\n%s: ", thingToSayToUser);
                String value = consoleIn.readLine();
                boolean passwIsSolid = passwordIsSolid(value);
                
                if (value != null && !value.isEmpty() && passwIsSolid)
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

    private static boolean passwordIsSolid(String input) 
    {
        if (input == null || input.isEmpty()) 
        {
            return false;
        }
        
        boolean haMaiuscola = false;
        boolean haNumero = false;
        
        for (char c : input.toCharArray()) 
        {
            if (Character.isUpperCase(c)) 
            {
                haMaiuscola = true;
            }
            if (Character.isDigit(c)) 
            {
                haNumero = true;
            }
            
            // Ottimizzazione: esci appena trovi entrambi
            if (haMaiuscola && haNumero) 
            {
                return true;
            }
        }
        
        return haMaiuscola && haNumero;
    }

    public static String getString (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
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

    //acquisizione array di stringhe
    public static ArrayList <String> getStringArray (String thingToSayToUser, String cycleConfirmation)
   {
        ArrayList<String> values = new ArrayList<>();
        boolean addMoreValues = true;
        
        while (addMoreValues)
        {
            try
            {
                System.out.printf("\n%s: ", thingToSayToUser);
                String value = consoleIn.readLine();
                
                if (value != null && !value.isEmpty())
                {
                    values.add(value);
                    addMoreValues = getYesNoAnswer(cycleConfirmation);
                }
                else
                {
                    System.out.println("Valore inserito non valido, riprova!\n");
                }
            }
            catch (IOException errorDuringDigitation)
            {
                System.out.println("\nErrore durante la digitazione: " + errorDuringDigitation.getMessage());
                System.out.println("Riprova");
            }
            
        }

        return values;
    }

    //acquisizione lasciando gli spazi
    public static String getStringNoTrim (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String value = consoleIn.readLine();
                
                if (value != null && !value.isEmpty())
                    return value;
                else
                    System.out.println ("Valore inserito non valido, riprova!\n");
            }
            catch (IOException errorDuringDigitation)
            {
                System.out.println ("\nErrore durante la digitazione" + errorDuringDigitation.getMessage());
                System.out.println ("Riprova");
            }
        }
    }

    public static String getStringNoTrimWithConfirm (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String value = consoleIn.readLine();
                
                if (value != null && !value.isEmpty())
                {
                    if (getYesNoAnswer("Hai inserito "+value+" confermi"))
                        return value;
                }
                else
                    System.out.println ("Valore inserito non valido, riprova!\n");
            }
            catch (IOException errorDuringDigitation)
            {
                System.out.println ("\nErrore durante la digitazione" + errorDuringDigitation.getMessage());
                System.out.println ("Riprova");
            }
        }
    }

    //acquisizione tenendo gli spazi con un num. max di caratteri
    public static String getStringNoTrim (String thingToSayToUser, int maxCharacters)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s(Max: %d caratteri): ", thingToSayToUser, maxCharacters);
                String value = consoleIn.readLine();
                
                if (value != null && !value.isEmpty())
                    return value;
                else if (value.length() > maxCharacters)
                    System.out.println ("\nStringa non valida, hai superato il numero massimo di caratteri consentiti");
                System.out.println ("\nValore inserito non valido, riprova!\n");
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
                {
                    String confirmInput = "Hai inserito "+input+" confermi";
                    if (getYesNoAnswer(confirmInput))
                        return Integer.parseInt(input);
                }
             } 
            catch (IOException errorDuringDigitation)
            {
                System.out.println ("\nErrore durante la digitazione" + errorDuringDigitation.getMessage());
                System.out.println ("Riprova");
            }
        }
    }

    public static int getIntegerNoCheck (String thingToSayToUser)
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

                if ((input == null || input.isEmpty()) && tmpValue < lowerBound && tmpValue > upperBound)
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

    public static boolean getYesNoAnswer (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s(SI/NO): ", thingToSayToUser);
                String answer = consoleIn.readLine();

                if (answer.toLowerCase().equals("si"))
                {
                    return true;
                }
                if (answer.toLowerCase().equals("no"))
                {
                    return false;
                }
                System.out.println ("\nErrore, valore inserito non valido");
            }
            catch (IOException errorDuringDigitation)
            {
                System.out.println ("\nErrore durante la digitazione" + errorDuringDigitation.getMessage());
                System.out.println ("Riprova");
            }
        }
    }

    public static String getDateFromUser (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String value = consoleIn.readLine();
                
                boolean isADate;
                if (value != null && !value.isEmpty())
                    return value;

                System.out.println ("Valore inserito non valido, riprova!\n");
            }
            catch (IOException errorDuringDigitation)
            {
                System.out.println ("\nErrore durante la digitazione" + errorDuringDigitation.getMessage());
                System.out.println ("Riprova");
            }
        }
        
    }

    public static void stamp_list (String title, Collection <String>listToStamp)
	{
		System.out.println ("\n"+title);
		for (String elementToStamp : listToStamp)
		{
			System.out.println (elementToStamp);
		}
	}

    public static void stamp_list (String title, Map <String, Place> placeList)
    {
        System.out.println ("\n"+title);
        for (Map.Entry<String, Place> entry : placeList.entrySet())
        {
            Place place = entry.getValue();
            System.out.printf ("A %s ci sono questi tipi di visite\n", place.getPlaceName());
            place.stampTypeVisit();
        }
    }

    public static void operationIsSuccessful (boolean result)
    {
        if (result)
            System.out.println ("\nL'operazione ha avuto successo");
        else
            System.out.println ("\nERRORE! l'operazione non ha avuto successo");
    }

}

