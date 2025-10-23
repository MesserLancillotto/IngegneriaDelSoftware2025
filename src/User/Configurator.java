<<<<<<< HEAD
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Configurator extends User 
{
    BufferedReader consoleIn = new BufferedReader (new InputStreamReader (System.in));
    ArrayList <Place> placeList = new ArrayList <> ();
	
    //in corso di implentazione
    HashMap <Integer, Runnable> menuSelection = new HashMap <>();
=======
package User;

import java.util.*;


public class Configurator extends User 
{
    ArrayList <Place> placeList = new ArrayList <> ();
	
    HashMap <Integer, Runnable> menuSelection = new HashMap <>();
    ArrayList <String> menuOptionList = new ArrayList<>();
>>>>>>> 112fcb7 (Spostato le classi nel package User, implementata classe per interazione con l'utente e sistemato buona parte del codice collegato a essa)

    private void initializeMenuSelection ()
    {
        menuSelection.put(1, () -> view_voluntary_list());
        menuSelection.put(2, () -> view_visitable_places());
        menuSelection.put(3, () -> view_type_of_visit());
        menuSelection.put(4, () -> view_type_of_visit());   // metodo per visualizzare le visite in stato di visita
        menuSelection.put(5, () -> modify_max_number_per_subscription());
        menuSelection.put(6, () -> manage_disponibilty_dates());  
<<<<<<< HEAD
=======

        menuOptionList.add("Visualizza l'elenco volontari");
        menuOptionList.add("Visualizza l'elenco dei luoghi visitabili");
        menuOptionList.add("Visualizza l'elenco dei tipi di visita associati a ciascun luogo");
        menuOptionList.add("Visualizza le visite in stato di visita");	// non ho capito cosa vuole
        menuOptionList.add("Modifica il numero massimo di persone iscrivibili a un'iniziativa da parte di un fruitore");
        menuOptionList.add("Segna date precluse alle visite");
>>>>>>> 112fcb7 (Spostato le classi nel package User, implementata classe per interazione con l'utente e sistemato buona parte del codice collegato a essa)
    }

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
<<<<<<< HEAD
        try
        {
            ArrayList <String> optionList = new ArrayList<>();
            optionList.add("Visualizza l'elenco volontari");
            optionList.add("Visualizza l'elenco dei luoghi visitabili");
            optionList.add("Visualizza l'elenco dei tipi di visita associati a ciascun luogo");
            optionList.add("Visualizza le visite in stato di visita");	// non ho capito cosa vuole
            optionList.add("Modifica il numero massimo di persone iscrivibili a un'iniziativa da parte di un fruitore");
            optionList.add("Segna date precluse alle visite");
		
            int optionCount = 1;
            for (String options : optionList)
            {
                System.out.println (optionCount+options);
            }
            System.out.println ("Cosa vuoi fare: ");
            menuSelection.get(consoleIn.readLine()).run();; // implementare controllo errori
        }
        catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		} 

=======
        int optionCount = 1;
        for (String options : menuOptionList)
        {
            System.out.println (optionCount+options);
        }
        menuSelection.get(UserTui.getInteger("Cosa vuoi fare", 0, optionCount+1))
                            .run();
>>>>>>> 112fcb7 (Spostato le classi nel package User, implementata classe per interazione con l'utente e sistemato buona parte del codice collegato a essa)
	}
	
	// costruttore per primo accesso del configuratore
	public Configurator (String userName, String cityOfResidence, int birthYear, String password)
	{
		this.userName = userName;
		this.cityOfResidence = cityOfResidence;
		this.birthYear = birthYear;
        this.password = password;
		roleTitle = "C";	// da decidere come gestire il titolo
		
        initializeMenuSelection();
		set_basic_app_configuration (); // configurazione base dell'app da fare al primo accesso del configuratore
		manage_options ();
	}

    // secondo costruttore, da decidere la discriminante per capire come decidere che non è il primo accesso -> per ora String tmp
    public Configurator (String userName, String cityOfResidence, int birthYear, String password, String tmp)
	{
		this.userName = userName;
		this.cityOfResidence = cityOfResidence;
		this.birthYear = birthYear;
        this.password = password;
		roleTitle = "C";	// da decidere come gestire il titolo
		
        initializeMenuSelection();
		manage_options ();
	}
	
	// metodo per fissare l'ambito territoriale e il numero max di persone iscrivibili dal fruitore (Vedi punto 3, Versione 1)
	public void set_basic_app_configuration ()
	{
<<<<<<< HEAD
		
		try
		{
			System.out.printf ("\nIn che ambito territoriale opera l'applicazione: ");
			String areaOfInterest = consoleIn.readLine();
            //client.makeServerRequest(areaOfInterest, maxPeopleForSubscription, areaOfInterest)
			
			System.out.printf ("Quante persone un fruitore dell'applicazione può iscrivere con una sola iscrizione: ");
			int maxPeopleForSubscription = Integer.parseInt(consoleIn.readLine());
            //client.makeServerRequest(areaOfInterest, maxPeopleForSubscription, areaOfInterest)
			
			make_new_association();    // metodo per creare 
			
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		} 
=======
		String areaOfInterest = UserTui.getString("\nIn che ambito territoriale opera l'applicazione");
        //client.makeServerRequest(areaOfInterest, maxPeopleForSubscription, areaOfInterest)
        
        int maxPeopleForSubscription = UserTui.getInteger("Quante persone un fruitore dell'applicazione può iscrivere con una sola iscrizione", 0, 100);
        //client.makeServerRequest(areaOfInterest, maxPeopleForSubscription, areaOfInterest)
        
        make_new_association();    // metodo per creare 
>>>>>>> 112fcb7 (Spostato le classi nel package User, implementata classe per interazione con l'utente e sistemato buona parte del codice collegato a essa)
	}
	
	public void manage_options ()
	{
<<<<<<< HEAD
        try
        {
            String keepUsingConfiguratorMenu;
            do
            {
                visualize_options();

                System.out.printf ("\nVuoi fare altro: ");
                keepUsingConfiguratorMenu = consoleIn.readLine();
            }while (keepUsingConfiguratorMenu.equals ("SI"));
        }
        catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		}
=======
        String keepUsingConfiguratorMenu;
        do
        {
            visualize_options();

            System.out.printf ("\nVuoi fare altro: ");
            keepUsingConfiguratorMenu = UserTui.getYesNoAnswer("\nVuoi fare altro");
        }while (keepUsingConfiguratorMenu.toUpperCase().toUpperCase().equals ("SI"));
>>>>>>> 112fcb7 (Spostato le classi nel package User, implementata classe per interazione con l'utente e sistemato buona parte del codice collegato a essa)
	}
	
    // metodo per la creazione delle prime associazioni (Vedi punto 3, Versione 1)
	public void make_new_association ()
	{
<<<<<<< HEAD
		try
		{
			String tmpVisitType;
			String tmpVoluntaryName;
			String newPlaceAnswer;

            do
            {
                System.out.printf ("Inserire un nuovo luogo per una visita guidata: ");
                String tmpPlace = consoleIn.readLine();
                
                String newTypeVisitAnswer; // discriminante del ciclo deprecabile con interfaccia grafica
                
                do 
                {
                    System.out.printf ("Inserire il tipo di visita che offre il luogo: ");
                    tmpVisitType = consoleIn.readLine();
                    
                    System.out.printf ("Inserire il volontario che segue questa visita: ");
                    tmpVoluntaryName = consoleIn.readLine();
                    // sulla scelta del volontario -> creare metodo che gli mostra quelli esistenti o che gli chiede di aggiugerne uno nuovo
                    
                    placeList.add(new Place (tmpPlace, tmpVisitType, tmpVoluntaryName));
                    //client.makeServerRequest(areaOfInterest, maxPeopleForSubscription, areaOfInterest)  
                    //invio al server il nuovo luogo

                    System.out.printf("Vuoi inserire un'altro tipo di visita associato a questo luogo: ");
                    newTypeVisitAnswer = consoleIn.readLine();
                } while (newTypeVisitAnswer.equals("SI"));	// interazione con l'utente da migliorare, prima valutare intefaccia grafica
                
                System.out.printf ("Vuoi inserire un'altro luogo: ");
                newPlaceAnswer = consoleIn.readLine();
            } while (newPlaceAnswer.equals ("SI"));     // come sopra, da sistemare con int. grafica o altro codice
            
		}
		catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		} 
=======
        String tmpVisitType;
        String tmpVoluntaryName;

        String addAnotherPlaceAnswer;
        String addAnotherTypeVisitAnswer; 
        //attributi usati come discriminante del ciclo

        do
        {
            String tmpPlace = UserTui.getString("Inserire un nuovo luogo per una visita guidata");
            
            
            do 
            {
                tmpVisitType = UserTui.getString("Inserire il tipo di visita che offre il luogo");
                tmpVoluntaryName = UserTui.getString("Inserire il volontario che segue questa visita");
                // sulla scelta del volontario -> creare metodo che gli mostra quelli esistenti o che gli chiede di aggiugerne uno nuovo
                
                placeList.add(new Place (tmpPlace, tmpVisitType, tmpVoluntaryName));
                //client.makeServerRequest(areaOfInterest, maxPeopleForSubscription, areaOfInterest)  
                //invio al server il nuovo luogo

                addAnotherTypeVisitAnswer = UserTui.getYesNoAnswer ("Vuoi inserire un'altro tipo di visita associato a questo luogo(SI/NO)");
            } while (addAnotherTypeVisitAnswer.toUpperCase().equals("SI")); // fine ciclo tipo visita
            
            System.out.printf ("Vuoi inserire un'altro luogo: ");
            addAnotherPlaceAnswer = UserTui.getYesNoAnswer("Vuoi inserire un'altro luogo");
        } while (addAnotherPlaceAnswer.toUpperCase().equals ("SI")); // fine ciclo luogo
>>>>>>> 112fcb7 (Spostato le classi nel package User, implementata classe per interazione con l'utente e sistemato buona parte del codice collegato a essa)
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

    public void modify_max_number_per_subscription()
    {
<<<<<<< HEAD
        try
        {
            int newMaxNumber;
            System.out.printf ("\nDefinire il nuovo numero massimo di persone che un fruitore può iscrivere in una volta sola: ");
            newMaxNumber = Integer.parseInt(consoleIn.readLine());

            //client.makeServerRequest(password, newMaxNumber, cityOfResidence);
            //invio al server il nuovo valore
        }
        catch (IOException errorDuringDigitation)
		{
			System.out.println ("Errore durante la digitazione" + errorDuringDigitation.getStackTrace());
		}  

=======
        int newMaxNumber = UserTui.getInteger("\nDefinire il nuovo numero massimo di persone che un fruitore può iscrivere in una volta sola", 0, 100);
        //client.makeServerRequest(password, newMaxNumber, cityOfResidence);
        //invio al server il nuovo valore
>>>>>>> 112fcb7 (Spostato le classi nel package User, implementata classe per interazione con l'utente e sistemato buona parte del codice collegato a essa)
    }

    public void view_voluntary_list()
    {
        //client.makeServerRequest(password, birthYear, cityOfResidence);
        //chiedo al server di mandarmi i voluntary
    }

    public void manage_disponibilty_dates ()
    {

    }
}
