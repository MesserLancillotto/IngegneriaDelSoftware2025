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
    private enum DayOfWeek
    {
        LUNEDI, MARTEDI, MERCOLEDI, GIOVEDI, VENERDI, SABATO, DOMENICA;

        public static boolean isValid(String value) 
        {
            if (value == null) 
                return false;
        
            try 
            {
                DayOfWeek.valueOf(value.toUpperCase());
                return true;
            } 
            catch (IllegalArgumentException e) 
            {
                return false;
            }
        }
    };
    private HashMap <String, DayOfWeek> dayOfWeekName = new HashMap <>();

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

    public static String getDayOfWeekFromUser (String thingToSayToUser)
    {
        String tmp;
        boolean dayIsValid;
        do
        {
            tmp = UserTui.getString(thingToSayToUser);
            dayIsValid = DayOfWeek.isValid(tmp.toUpperCase());
            if (!dayIsValid)
            {
                System.out.println ("Il giorno inserito non è valido, riprova!");
                System.out.println ("I giorni validi sono: Lunedi, Martedi, Mercoledi, Giovedi, Venerdi, Sabato, Domenica");
            }
        }while (!dayIsValid);
        StringBuilder sb = new StringBuilder();
        sb.append(tmp.substring(0,1).toUpperCase());
        sb.append(tmp.substring(1, 3));
        return sb.toString();
    }

    public static int getAnHourFromUser (String thingToSayToUser)
    {
        String tmpHour;
        boolean hourIsValid;
        do
        {
            tmpHour = UserTui.getString(thingToSayToUser);
            hourIsValid = checkSpecificHourFormat(tmpHour);
            if (!hourIsValid)
                System.out.println ("\nOrario inserito non valido!");
        }while (!hourIsValid);
        int tmpHours = Integer.parseInt(tmpHour.substring(0, 2));
        int tmpMinutes = Integer.parseInt(tmpHour.substring(3, 5));
        int completeHour = tmpHours * 100+tmpMinutes;
        return completeHour;
    }
    public static boolean checkSpecificHourFormat (String hour)
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
            int tmpHours = Integer.parseInt(hour.substring(0, 2));
            int tmpMinutes = Integer.parseInt(hour.substring(3, 5));
            
            // Verifica i range validi
            if (tmpHours >= 0 && tmpHours <= 23 && tmpMinutes >= 0 && tmpMinutes <= 59)
                return true;
            System.out.println ("\nOrario inserito non valido!");
            return false;
            
        } 
        catch (NumberFormatException e) 
        {
            return false;
        }
    }

    public static boolean isSameDay(long firstUnixDate, long secondUnixDate) 
    {
        // Controllo base per valori non validi
        if (firstUnixDate < 0 || secondUnixDate < 0) 
        {
            //System.err.println("Timestamp non validi: " + firstUnixDate + ", " + secondUnixDate);
            return false;
        }
        try 
        {
            LocalDate firstDate = Instant.ofEpochSecond(firstUnixDate)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
                    
            LocalDate secondDate = Instant.ofEpochSecond(secondUnixDate)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            
            boolean sameDay = firstDate.equals(secondDate);
            
            /*  Log opzionale per debug
            if (sameDay) 
            {
                System.out.println("Le date appartengono allo stesso giorno: " + firstDate);
            }*/
            return sameDay;
        } 
        catch (DateTimeException e) 
        {
            //System.err.println("Errore di conversione date: " + e.getMessage());
            return false;
        } 
        catch (Exception e) 
        {
            //System.err.println("Errore imprevisto: " + e.getMessage());
            return false;
        }
    }

}
