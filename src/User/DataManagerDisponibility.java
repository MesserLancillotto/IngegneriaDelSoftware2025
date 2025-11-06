package User;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


public class DataManagerDisponibility extends DataManager
{
    private Set <Integer> enteredDates = new HashSet<>();

    private int referenceYear;
    private int referenceMonth;
    private int endDayOfClosure;

    public DataManagerDisponibility (int reference)
    {
        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		String formattedData = data.format(formatter);

        this.day = Integer.parseInt(formattedData.substring(0, 2));
        this.month = Integer.parseInt(formattedData.substring(2, 4));
        this.year = Integer.parseInt(formattedData.substring(4));
        this.referenceYear = this.year;

        if (day < 16)
            referenceMonth = getIncreasedMonth(reference-1, month);
        else
            referenceMonth = getIncreasedMonth(reference, month);
        initialize_month_association(referenceYear);
    }

    public int getReferenceDay (String thingToSayForDay, String thingToSayForDuration)
    {
        boolean confirmDate;
        String userMessage = thingToSayForDay+" nel mese di "+monthNumberToName.get(referenceMonth)+ " (formato: DD):";
        
        int howManyDaysClosed;
        int loopCount = 0;
        int unaviableDay;

        do
        {
            if (loopCount >= 3)
            {
                System.out.println ("\nHai sbagliato troppe volte!");
                return -1;
            }
            unaviableDay = UserTui.getInteger(userMessage, 0, daysInAMonth.get(referenceMonth)+1);
            confirmDate = UserTui.getYesNoAnswer("Hai inserito il giorno "+unaviableDay+" confermi");

            if (enteredDates.contains(unaviableDay) && confirmDate)
            {
                System.out.println ("Hai già inserito questa data scegline un'altra!");
                return -1;
            }
            if (confirmDate)
            {
                int daysToTheEndOfMonth = daysInAMonth.get(referenceMonth) - unaviableDay;
                howManyDaysClosed = UserTui.getInteger(thingToSayForDuration, 0,Math.min(8, daysToTheEndOfMonth+1));
                if (howManyDaysClosed == 1)
                    endDayOfClosure = setToEndOfDay(unaviableDay); 
                else
                    endDayOfClosure = setToEndOfDay(unaviableDay+howManyDaysClosed);
                enteredDates.add(unaviableDay);
            }
            loopCount++;
        }while (!confirmDate);

        return unaviableDay;
    }

    //CONTROLLA, metodo per prendere un giorno solo
    public int getReferenceDay (String thingToSayForDay)
    {
        boolean confirmDate;
        String userMessage = thingToSayForDay+" nel mese di "+monthNumberToName.get(referenceMonth)+ " (formato: DD):";
        
        int loopCount = 0;
        int unaviableDay;

        do
        {
            if (loopCount >= 3)
            {
                System.out.println ("\nHai sbagliato troppe volte!");
                return -1;
            }
            unaviableDay = UserTui.getInteger(userMessage, 0, daysInAMonth.get(referenceMonth)+1);
            confirmDate = UserTui.getYesNoAnswer("Hai inserito il giorno "+unaviableDay+" confermi");

            if (enteredDates.contains(unaviableDay) && confirmDate)
            {
                System.out.println ("Hai già inserito questa data scegline un'altra!");
                return -1;
            }
            loopCount++;
        }while (!confirmDate);

        return unaviableDay;
    }

    private int getIncreasedMonth (int increase, int tmpMonth)
    {
        for (int i = 0; i < increase; i++)
        {
            tmpMonth += 1;
            if (tmpMonth > 12)
            {
                tmpMonth = 1;
                referenceYear +=1;
            }
        }

        return tmpMonth;
    }

    public long getUnixDate (int tmpDay)
    {
        LocalDate date = LocalDate.of(referenceYear, referenceMonth, tmpDay);
        return date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }


    public static int getDate ()
    {
        String tmpDate = UserTui.getString("Inserisci la data (formato: GG/MM/YYYY)");
        return 0;
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

    public int getEndDayOfClosure ()
    {
        return endDayOfClosure;
    }
}
