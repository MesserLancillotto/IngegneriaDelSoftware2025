package User;

import java.util.*;
import java.time.*;
import java.time.format.*;

public class DataManager 
{
    public int day;
    public int month;
    public int year;
    
    public HashMap <Integer, String> monthNumberToName = new HashMap <>();
    public HashMap <Integer, Integer> daysInAMonth = new HashMap<>();

    public void initialize_month_association (int toCheckLeapYear)
    {
        monthNumberToName.put(1, "Gennaio");
        monthNumberToName.put(2, "Febbraio");
        monthNumberToName.put(3, "Marzo");
        monthNumberToName.put(4, "Aprile");
        monthNumberToName.put(5, "Maggio");
        monthNumberToName.put(6, "Giugno");
        monthNumberToName.put(7, "Luglio");
        monthNumberToName.put(8, "Agosto");
        monthNumberToName.put(9, "Settembre");
        monthNumberToName.put(10, "Ottobre");
        monthNumberToName.put(11, "Novembre");
        monthNumberToName.put(12, "Dicembre");

        daysInAMonth.put(1, 31);
        if ((toCheckLeapYear % 4 == 0) && (toCheckLeapYear % 100 != 0 || toCheckLeapYear % 400 == 0))
            daysInAMonth.put(2, 29);
        else
            daysInAMonth.put(2, 28);
        daysInAMonth.put(3, 31);
        daysInAMonth.put(4, 30);
        daysInAMonth.put(5, 31);
        daysInAMonth.put(6, 30);
        daysInAMonth.put(7, 31);
        daysInAMonth.put(8, 31);
        daysInAMonth.put(9, 30);
        daysInAMonth.put(10, 31);
        daysInAMonth.put(11, 30);
        daysInAMonth.put(12, 31);
    }

    public static int setToEndOfDay(long unixTimestamp)
    {
        Instant instant = Instant.ofEpochSecond(unixTimestamp);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        
        ZonedDateTime endOfDay = zonedDateTime.with(LocalTime.MAX);
        int endOfDayDate = (int)endOfDay.toEpochSecond();
        
        return endOfDayDate;
    }

    public static String fromUnixToNormal(int value) 
    {
        try 
        {
            // Verifica che il timestamp non sia negativo
            if (value < 0) 
            {
                return "Data non valida";
            }
            
            // Converti il timestamp Unix in LocalDateTime
            Instant instant = Instant.ofEpochSecond(value);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            
            // Definisci il formato italiano
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'alle' HH:mm");
            
            // Formatta la data
            return dateTime.format(formatter);
            
        } 
        catch (Exception e) 
        {
            return "Data non valida";
        }
    }

}
