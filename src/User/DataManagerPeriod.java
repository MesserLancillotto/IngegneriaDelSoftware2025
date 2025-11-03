package User;

import java.time.LocalDate;
import java.time.ZoneId;

public class DataManagerPeriod extends DataManager
{
    private int startDate;
    private int endDate;

    int hours;
    int minutes;

    public DataManagerPeriod ()
    {
        super();
        boolean periodIsUnvalid = true;
        startDate = acquireDate("Inserisci la data di inizio (formato: DD/MM/YYYY)", "Inserisci l'ora di inizio (formato: HH:MM)");

        while (periodIsUnvalid)
        {
            endDate = acquireDate("Inserisci la data di fine (formato: DD/MM/YYYY)", "Inserisci l'ora di fine (formato: HH:MM)");
            if (endDate > startDate)    //controllo che la data sia dopo quella di inizio
                periodIsUnvalid = false;    
            else
                System.out.println ("Errore la data che hai inserito è prima della data di inizio dell'evento!");
        }
        
    }

    public int getStartDate ()
    {
        return startDate;
    }

    public int getEndDate ()
    {
        return endDate;
    }

    private int acquireDate (String thingToSayToUser, String thingToSayToUserAboutHour)
    {
        String tmpDate;
        String tmpHour;
        do
        {
            tmpDate = UserTui.getString(thingToSayToUser);
        }while (checkDateFormat(tmpDate));
        do
        {
            tmpHour = UserTui.getString(thingToSayToUserAboutHour);
        }while (checkHourFormat(tmpHour));
        return getUnixDate();
    }

    private boolean checkHourFormat (String hour)
    {
        if (hour == null || hour.length() != 5) 
        {
            return false;
        }
        // Verifica che il separatore sia ":"
        if (hour.charAt(2) != ':') 
        {
            return false;
        }
        
        try 
        {
            // Estrae ore e minuti
            hours = Integer.parseInt(hour.substring(0, 2));
            minutes = Integer.parseInt(hour.substring(3, 5));
            
            // Verifica i range validi
            if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59)
                return true;
            System.out.println ("\nOrario inserito non valido!");
            return false;
            
        } 
        catch (NumberFormatException e) 
        {
            return false;
        }
    }

    public boolean checkDateFormat (String dateString)
    {
        if (dateString == null || dateString.length() != 10) 
        {
            return false;
        }
    
         // Verifica il formato base: DD/MM/YYYY
        if (dateString.charAt(2) != '/' || dateString.charAt(5) != '/') 
        {
            return false;
        }
    
        try 
        {
            // Estrae giorno, mese e anno
            day = Integer.parseInt(dateString.substring(0, 2));
            month = Integer.parseInt(dateString.substring(3, 5));
            year = Integer.parseInt(dateString.substring(6, 10));
            
            // Verifica i range base
            if (year < 2025 || year > 2030) 
            {
                System.out.println ("\nErrore l'anno inserito non è valido");
                return false;
            }
            
            int checkDay = daysInAMonth.get(month);
            if (day > checkDay)
            {
                System.out.println ("\nErrore il giorno inserito non è valido");  
                return false;   //controllo che il giorno esista
            }
            return true;
        } 
         catch (NumberFormatException e) 
        {
            return false;
        }
        catch (Exception e)
        {
            //controllo che il mese esista
            System.out.println ("\nErrore il mese inserito non è valido");
            return false;
        }
    }

    public int getUnixDate ()
    {
        LocalDate date = LocalDate.of (year, month, day);
        int unixDate = (int) date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();

        return unixDate;
    }
}
