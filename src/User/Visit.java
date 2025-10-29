package User;
import java.util.*;

public class Visit 
{
	private String date;
	private int visitorLowerBound;
	private int visitorUpperBound;
	private String place;
	private String visitType;
	private enum stateOfVisit {PROPOSTA, COMPLETA, CONFERMATA, CANCELLATA, EFFETTUATA};	
	
	public Visit (String date, String place, String visitType)
	{
		this.date = date;
        this.place = place;
        this.visitType = visitType;
	}

	public String getVisitType ()
	{
		return visitType;
	}
}
