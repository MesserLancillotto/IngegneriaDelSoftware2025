package Client;

import java.io.*;
import java.net.*;
import java.util.*;

public final class Client 
{
    private static ServerSocket serverSocket = null;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;

    private static final int PORT = 8000;
    private static final String SERVER_ADDR = "127.0.0.1";

    private static String userID;
    private static String userPassword;
    //private static RequestType body;
    //private static ComunicationType comunicationType;

    private static Client instance;

    /*
    public Client(
        String userID,
        String userPassword
    ) {
        
        this.userID = userID;
        this.userPassword = userPassword;
        
    }
    */

    private Client ()
    {
        
    }

    public static synchronized Client getInstance ()
    {
         if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void setUserID (String tmpuUserID)
    {
        userID = tmpuUserID;
    }

    public void setUserPassword (String tmpUserPassword)
    {
        userPassword = tmpUserPassword;
    }

    public void delete_place(String city, String address)
    {
        /* 
        this.body = new DeletePlaceRequest(city, address);
        this.comunicationType = ComunicationType.DELETE_PLACE;
        */
    }

    public void edit_event(String eventName, Map<String, Object> fields)
    {
        /*
        RequestType body = new EditEventRequest(eventName, fields);
        comunicationType = ComunicationType.EDIT_EVENT;
        */
    }
    public void edit_password(String newPassword)
    {
        /*
        RequestType body = new EditPasswordRequest(newPassword);
        comunicationType = ComunicationType.EDIT_PASSWORD;
        */
    }
    public void get_event(Map<String, Object> filters) 
    {
        /*
        RequestType body = new GetEventRequest(filters);
        comunicationType = ComunicationType.GET_EVENT;
        */
    }

    public void get_user_data(String target)
    {
        /*
        RequestType body = new GetUserDataRequest(target);
        comunicationType = ComunicationType.GET_USER_DATA;
        */
    }
    public void get_voluntaries_for_visit(String event)
    {
        /*
        RequestType body = new GetVoluntariesForVisitRequest(event);
        comunicationType = ComunicationType.GET_VOLUNTARIES_FOR_VISIT;
        */
    }

    public void get_voluntaries(Map<String, Object> filters)
    {
        /* 
        this.body = new GetVoluntariesRequest(filters);
        this.comunicationType = ComunicationType.GET_VOLUNTARIES;
        */
    }

    public void set_closed_days(
        int startDate, 
        int endDate,
        String organization
    ) {
        /*
        RequestType body = new SetClosedDaysRequest(startDate, endDate, organization);
        comunicationType = ComunicationType.SET_CLOSED_DAYS;
        */
    }

    public void set_disponibility (
        String event,
        int time
    )
    {
        
    }
    public void set_new_event(
        String eventName,
        String description,
        String city,
        String address,
        String meetingPoint,
        int startDate,
        int endDate,
        String organizationName,
        int minimumUsers,
        int maximumUsers,
        int maximumFriends,
        String visitType,
        float price,
        ArrayList<String> visitDays,
        ArrayList<Integer> startHour,
        ArrayList<Integer> duration
    ) {
        /*
        RequestType body = new SetNewEventRequest(
            eventName,
            description,
            city,
            address,
            startDate,
            endDate,
            organizationName,
            minimumUsers,
            maximumUsers,
            maximumFriends,
            visitType
        );
        comunicationType = ComunicationType.SET_NEW_EVENT;
        */
    }
    public void set_new_organization(        
        String organizationName,
        ArrayList<String> territoriesOfCompetence
    ) {
        /*
        RequestType body = new SetNewOrganizationRequest(organizationName, territoriesOfCompetence);
        comunicationType = ComunicationType.SET_NEW_ORGANIZATION;
        */
    }
    public void set_new_user(
        String userName,
        String newPassword,
        String cityOfResidence,
        Integer birthYear
    ) {
        /*
        RequestType body = new SetNewUserRequest(
            userName,
            newPassword,
            cityOfResidence,
            birthYear
        );
        comunicationType = ComunicationType.SET_NEW_USER;
        */
    }

     public void set_voluntary_to_event(
        String event,
        String targetID,
        int time
    ) {
        /* 
        this.body = new SetVoluntaryToEventRequest(
            event,
            targetID,
            time
        );
        this.comunicationType = ComunicationType.SET_VOLUNTARY_TO_EVENT;
        */
    }

    public String make_server_request()
    {
        /*
        String request = new Request(comunicationType, userID, userPassword, body).toJSONString();
        String response = "";
        try
        (
            Socket socket = new Socket(SERVER_ADDR, PORT);
        )
        {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(request);
            dataOutputStream.flush(); 
            dataInputStream = new DataInputStream(socket.getInputStream());
            response = dataInputStream.readUTF();
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
            return response;
        }
        catch(Exception e) 
        {
            System.out.println("An error occurred: " + e);
        }
        return response;
        */
        return "";
    }
}