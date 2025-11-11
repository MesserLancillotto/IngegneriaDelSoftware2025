package User;

import java.util.*;

public class VoluntaryData 
{
    private String userName;
    private String userNameForComparison;
    private ArrayList <String> allowedVisitType;
    public VoluntaryData (String userName, ArrayList <String> allowedVisitType)
    {
        this.userName = userName;
        this.userNameForComparison = userName.toUpperCase();
        this.allowedVisitType = allowedVisitType;
    }

    public String getUserName ()
    {
        return userName;
    }
    public String getUserNameForComparison ()
    {
        return userNameForComparison;
    }
    public ArrayList<String> getAllowedVisitTypes ()
    {
        return allowedVisitType;
    }
}
