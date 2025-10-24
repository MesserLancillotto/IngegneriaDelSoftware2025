import java.util.*;

public class Visit 
{
    
	ArrayList <Voluntary> voluntaries;
	int date;
	int visitorLowerBound;
	int visitorUpperBound;
	String place;
	String visitType;
	enum stateOfVisit {PROPOSTA, COMPLETA, CONFERMATA, CANCELLATA, EFFETTUATA};	
	
	public Visit (int date, String place, String visitType)
	{
		this.date = date;
        this.place = place;
        this.visitType = visitType;
	}
}
