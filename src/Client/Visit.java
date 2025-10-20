package Client;

import java.util.ArrayList;

public class Visit 
{
    
	ArrayList <Voluntary> voluntaries;
	int date;
	int visitorLowerBound;
	int visitorUpperBound;
	String place;
	String visitType;
	enum stateOfVisit {PROPOSTA, COMPLETA, CONFERMATA, CANCELLATA, EFFETTUATA};	
	
	public Visit ()
	{
		
	}
}
