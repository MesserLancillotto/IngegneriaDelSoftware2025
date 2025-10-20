package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Configurator extends User 
{
    ArrayList <Place> placeList = new ArrayList <> ();
	
	public void stamp_list (String title, Collection <String>listToStamp)
	{
		System.out.println (title);
		for (String elementToStamp : listToStamp)
		{
			System.out.println (elementToStamp);
		}
	}
	
	public void visualize_options ()
	{
		System.out.println ("Cosa vuoi fare: ");
		ArrayList <String> optionList = new ArrayList<>();
		optionList.add("Visualizza l'elenco volontari");
		optionList.add("Visualizza l'elenco dei luoghi visitabili");
		optionList.add("Visualizza l'elenco dei tipi di visita associati a ciascun luogo");
		// usare set sull'array list dei luoghi
		optionList.add("Visualizza le visite in stato di visita");	// non ho capito cosa vuole
		optionList.add("Modifica il numero massimo di persone iscrivibili a un'iniziativa da parte di un fruitore");
		optionList.add("Segna date precluse alle visite");
		
		int optionCount = 1;
		for (String options : optionList)
		{
			System.out.println (optionCount+options);
		}
	}
	
	// fare due costruttori uno per il primo accesso e uno senza set_basic_app_configuration
	public Configurator (String userName, String cityOfResidence, int birthYear)
	{
		this.userName = userName;
		this.cityOfResidence = cityOfResidence;
		this.birthYear = birthYear;
		roleTitle = "C";	// da decidere come gestire il titolo
		
		set_basic_app_configuration (); // indicare ambito territoriale e numero max di persone iscrivibili da un fruitore
		manage_options ();
	}
	
	// metodo per fissare l'ambito territoriale e il numero max di persone iscrivibili dal fruitore
	public void set_basic_app_configuration ()
	{
		BufferedReader consoleIn = new BufferedReader (new InputStreamReader (System.in));
		
		try
		{
			System.out.printf ("\nIn che ambito territoriale opera l'applicazione: ");
			String areaOfInterest = consoleIn.readLine();
			
			System.out.printf ("Quante persone un fruitore dell'applicazione può iscrivere con una sola iscrizione: ");
			int maxPeopleForSubscription = Integer.parseInt(consoleIn.readLine());
			// aggiungere controlli inserimento
			
			make_new_association(consoleIn);
			
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		} 
	}
	
	public void manage_options ()
	{
		visualize_options();
	}
	
	public void make_new_association (BufferedReader consoleIn)
	{
		try
		{
			String tmpVisitType;
			String tmpVoluntaryName;
			
			System.out.printf ("Inserire un nuovo luogo per una visita guidata: ");
			String tmpPlace = consoleIn.readLine();
			
			String answer; // discriminante del ciclo deprecabile con interfaccia grafica
			
			do 
			{
				System.out.printf ("Inserire il tipo di visita che offre il luogo: ");
				tmpVisitType = consoleIn.readLine();
				
				System.out.printf ("Inserire il volontario che segue questa visita: ");
				tmpVoluntaryName = consoleIn.readLine();
				
				System.out.printf("Vuoi inserire un'altro tipo di visita associato a questo luogo: ");
				answer = consoleIn.readLine();
			} while (answer.equals("SI"));	// interazione con l'utente da migliorare, prima valutare intefaccia grafica
			// sulla scelta del volontario -> creare metodo che gli mostra quelli esistenti o che gli chiede di aggiugerne uno nuovo
			
			placeList.add(new Place (tmpPlace, tmpVisitType, tmpVoluntaryName));
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		} 
	}
	
	public void view_visitable_places ()
	{
		Set <String> distinctPlaces = new HashSet <> ();
		for (Place p : placeList)
		{
			distinctPlaces.add (p.placeName);
		}
		stamp_list ("Questi sono i posti visitabili: ", distinctPlaces);
	}
	
	public void view_type_of_visit ()
	{
		for (Place p: placeList)
		{
			String msg = "Ecco i tipi di visita associati a" + p.placeName+":"; 
			stamp_list (msg, p.get_type_visit_list());
		}
	}    
}
