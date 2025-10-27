import java.util.*;
import java.time.*;
import java.time.format.*;

public class DataManager 
{
    private int day;
    private int month;
    private int referenceMonth;
    private int year;
    
    private HashMap <Integer, String> monthNumberToName = new HashMap <>();
    private HashMap <Integer, Integer> daysInAMonth = new HashMap<>();

    public void initialize_month_association ()
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
        if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0))
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

    public DataManager ()
    {
        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		String formattedData = data.format(formatter);

        this.day = Integer.parseInt(formattedData.substring(0, 2));
        this.month = Integer.parseInt(formattedData.substring(2, 4));
        this.year = Integer.parseInt(formattedData.substring(4));

        if (day < 16)
            referenceMonth = getIncreasedMonth(2, month);
        else
            referenceMonth = getIncreasedMonth(3, month);

    }

    public String getUnaviableDay ()
    {
        String tmpDay;
        String confirmDate;
        String userMessage = "Inserire il giorno precluso alle visite nel mese di "+monthNumberToName.get(referenceMonth)+ " (DD):";
        
        int unaviableDate;

        do
        {
            unaviableDate = UserTui.getInteger(userMessage, 0, daysInAMonth.get(referenceMonth)+1);
            confirmDate = UserTui.getYesNoAnswer("Hai inserito il giorno "+unaviableDate+" confermi (SI/NO): ");
        }while (confirmDate.toUpperCase().equals("NO"));

        tmpDay = Integer.toString(unaviableDate);
        return tmpDay;
    }

    private int getIncreasedMonth (int increase, int tmpMonth)
    {
        for (int i = 0; i < increase; i++)
        {
            tmpMonth += 1;
            if (tmpMonth > 12)
                tmpMonth = 1;
        }

        return tmpMonth;
    }

    public int getReferenceMonth ()
    {
        return referenceMonth;
    }

    public String getMonthName (int month)
    {
        return monthNumberToName.get(month);
    }

    public int getYear ()
    {
        return year;
    }

}
