package User;

import java.util.*;


import org.json.*;

public class JSONObjectMethod 
{
    /* 
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
        */
    public static ArrayList<String> jsonArrayConverter(JSONArray jsonArray) 
    {
        ArrayList<String> list = new ArrayList<>();
        
        if (jsonArray == null) 
        {
            return list; 
        }
        
        for (int i = 0; i < jsonArray.length(); i++) 
        {
            // Usa optString per evitare eccezioni
            String value = jsonArray.optString(i, null);
            if (value != null) 
            {
                list.add(value);
            }
        }
        
        return list;
    }
}
