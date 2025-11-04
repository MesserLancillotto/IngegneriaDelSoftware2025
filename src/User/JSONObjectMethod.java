package User;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class JSONObjectMethod 
{
    public static String getJsonValue(String input, String key) 
    {
        if (input == null || key == null) return null;

        String s = input.trim();

        // 1) Prova il formato JSON standard con chiavi tra virgolette:
        //    "key" : "value"   oppure   "key": value
        String jsonRegex = "\""+Pattern.quote(key)+"\"\\s*:\\s*(\"([^\"]*)\"|[^,}\\]\\n\\r]+)";
        Pattern pJson = Pattern.compile(jsonRegex);
        Matcher mJson = pJson.matcher(s);
        if (mJson.find()) 
        {
            String group = mJson.group(1);
            if (group == null) return null;
            group = group.trim();
            if (group.startsWith("\"") && group.endsWith("\"") && group.length() >= 2) 
            {
                return group.substring(1, group.length() - 1);
            } 
            else 
            {
                // rimuove eventuali separatori finali
                return trimTrailingSeparators(group);
            }
        }

        // 2) Prova il formato libero senza virgolette: key:someValue
        //    Cattura tutto fino al prossimo separatore (tab/newline/space/,/;/}])
        String looseRegex = "(?:\\b" + Pattern.quote(key) + "\\b)\\s*:\\s*([^\\t\\n\\r,;\\]}]+)";
        Pattern pLoose = Pattern.compile(looseRegex);
        Matcher mLoose = pLoose.matcher(s);
        if (mLoose.find()) 
        {
            String val = mLoose.group(1);
            if (val == null) return null;
            return trimTrailingSeparators(val.trim());
        }

        // 3) Ultimo tentativo: se le coppie sono separate solo da \t senza : contigui (edge-case)
        //    es: "key:value" è il formato atteso, ma se ci sono casi diversi non coperti -> null
        return null;
    }

    private static String trimTrailingSeparators(String v) 
    {
        // Rimuove eventuali virgole, punti, tab, spazio finale
        return v.replaceAll("[\\t\\n\\r\\s,;]+$", "");
    }

    public static String extractUserID(String jsonString) 
    {
        if (jsonString == null) return "";
        
        try 
        {
            // Pattern che gestisce spazi, tab e formattazioni varie
            Pattern pattern = Pattern.compile("\"userID\"\\s*:\\s*\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(jsonString);
            
            if (matcher.find()) 
            {
                return matcher.group(1).trim();
            }
        } 
        catch (Exception e) 
        {
            System.err.println("Errore nell'estrazione: " + e.getMessage());
        }
        
        return "";
    }

    public static boolean extractBoolean(String jsonString, String fieldName) 
    {
        String value = getJsonValue(jsonString, fieldName);
        return value.toUpperCase().equals("TRUE");
    }

    public static ArrayList<String> extractArray(String json, String pattern) 
    {
        ArrayList<String> values = new ArrayList<>();
        
        // Crea il pattern regex per cercare "pattern":"value"
        String regex = "\"" + Pattern.quote(pattern) + "\"\\s*:\\s*\"([^\"]*)\"";
        Pattern compiledPattern = Pattern.compile(regex);
        Matcher matcher = compiledPattern.matcher(json);
        
        // Trova tutte le occorrenze
        while (matcher.find()) 
        {
            values.add(matcher.group(1));
        }
    
        return values;
    }

    public static void confirmRequest (String json, String field)
    {
         if (JSONObjectMethod.extractBoolean(json, field))
                    System.out.println ("\nDati correttamente salvati!");
                else
                    System.out.println ("\nErrore nel salvare i dati sul server!");
    }

    public static ArrayList <Visit> getVisitArray (String json)
    {
        return null;
    }
    ////////////////
    

    public static ArrayList <String> jsonArrayConverter (JSONArray jsonArray)
    {
        try
        {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) 
            {
                list.add(jsonArray.getString(i));
            }

            return list;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
