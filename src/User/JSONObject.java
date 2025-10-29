package User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONObject 
{
   public static String extractBooleanValue(String jsonString, String fieldName) 
	{
        if (jsonString == null || fieldName == null) 
        {
            return "FALSE";
        }
        
        
        String pattern = "\"" + fieldName + "\":\"([^\"]+)\"";
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(jsonString);
        
        if (matcher.find()) {
            String value = matcher.group(1);
            if ("TRUE".equals(value) || "FALSE".equals(value)) {
                return value;
            }
        }
        
        return "FALSE"; 
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
        String value = extractBooleanValue(jsonString, fieldName);
        return "TRUE".equals(value);
    }
}
