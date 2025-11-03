package User;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class JSONObjectCreator 
{
    private static final String CONFIG_FILE = "config.json";

    // GETTER - legge direttamente dal file JSON
    public static int getMaxPeopleForSubscription() 
    {
        try 
        {
            if (!Files.exists(Paths.get(CONFIG_FILE))) 
            {
                return 0; // Valore di default se il file non esiste
            }
            
            String content = new String(Files.readAllBytes(Paths.get(CONFIG_FILE)));
            
            // Estrazione del valore dal JSON
            String[] lines = content.split("\n");
            for (String line : lines) 
            {
                if (line.contains("maxPeopleForSubscription")) 
                {
                    // Rimuove tutto tranne i numeri
                    String value = line.replaceAll("[^0-9]", "").trim();
                    return Integer.parseInt(value);
                }
            }
            
        } 
        catch (IOException e) 
        {
            System.err.println("Errore durante la lettura: " + e.getMessage());
        } 
        catch (NumberFormatException e) 
        {
            System.err.println("Errore nel formato del file JSON: " + e.getMessage());
        }
        
        return 0; // Valore di default in caso di errore
    }

    // SETTER - scrive direttamente nel file JSON
    public static void setMaxPeopleForSubscription(int maxPeopleForSubscription) 
    {
        try (FileWriter file = new FileWriter(CONFIG_FILE)) 
        {
            String json = "{"
                + "\n"
                + "  \"maxPeopleForSubscription\": " + maxPeopleForSubscription + "\n"
                + "}";
            file.write(json);
            System.out.println("Valore aggiornato nel file JSON: " + maxPeopleForSubscription);
            
        } 
        catch (IOException e) 
        {
            System.err.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    // Metodo per verificare se il file di configurazione esiste
    public boolean configExists() 
    {
        return Files.exists(Paths.get(CONFIG_FILE));
    }
}

