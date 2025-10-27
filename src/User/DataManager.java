import java.util.*;
import java.time.*;
import java.time.format.*;

public class DataManager 
{
    private int day;
    private int month;
    private int year;
    private HashMap <Integer, String> monthNumberToName = new HashMap <>();

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
    }

    public DataManager ()
    {
        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		String formattedData = data.format(formatter);

        this.day = Integer.parseInt(formattedData.substring(0, 2));
        this.month = Integer.parseInt(formattedData.substring(2, 4));
        this.year = Integer.parseInt(formattedData.substring(4));
    }

    public void getDisponibilityDates ()
    {
        if (day < 16)
        {
            //i+2
            //UserTui.getDates(monthNumberToName.get(month-1), monthNumberToName(month), month-1, month);
        }
        else
        {
            //i+3
        }
    }

    public String getMonthName (int monthNumber)
    {
        return monthNumberToName.get(monthNumber);
    }

    public int getDayNumber ()
    {
        return day;
    }
    public int getMonthNumber ()
    {
        return month;
    }
    public int getYearNumber ()
    {
        return year;
    }
}
