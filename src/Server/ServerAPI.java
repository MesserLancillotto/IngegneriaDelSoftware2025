package Server;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import org.json.*;
import java.util.concurrent.*;

import RequestReply.Reply.*;
import RequestReply.Request.*;
import RequestReply.ComunicationType.*;
import RequestReply.UserRoleTitle.*;
import Server.Engine.*;

class ServerAPI extends Thread
{
    private static Socket socket = null;
    private static ServerSocket serverSocket = null;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;

    private static final int PORT = 8000;

    public static final void handleUserRequest() 
    {
        try
        {
            System.out.println("Server started on port " + PORT);
            serverSocket = new ServerSocket(PORT);
            socket = serverSocket.accept();
            System.out.println("Connection from device " 
                + socket.getInetAddress().getHostAddress());
            dataInputStream = new DataInputStream(
                new BufferedInputStream(
                    socket.getInputStream()
                )
            );
            
            String request = dataInputStream.readUTF();
            String response = userResponse(request);

            System.out.println("Request:\n-----------");
            System.out.println(request);
            System.out.println("-----------");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(response);
            dataOutputStream.flush();
            dataInputStream.close();
            dataOutputStream.close();
            serverSocket.close();
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println("An error occurred: " + e);
        } 
    }

    private static String userResponse(String request)
    {
        JSONObject dictionary = new JSONObject(request);
        Engine engine;
        ComunicationType comunicationType;
        try
        {
            comunicationType = ComunicationType.valueOf((String)dictionary.get("requestType"));
            String userID = dictionary.getString("userID"); 
            String userPassword = dictionary.getString("userPassword");
            String organization;
            Map<String, Object> filters;
            dictionary.remove("userID");
            dictionary.remove("userPassword");
            switch(comunicationType)
            {
                case EDIT_EVENT:
                    Map<String, Object> map = new HashMap<String, Object>();
                    for(String key : dictionary.keySet())
                    {
                        map.put(key, dictionary.get(key));
                    }
                    return new EditEventEngine(
                        userID,
                        userPassword,
                        map
                    ).handleRequest();
                case EDIT_PASSWORD:
                    return new EditPasswordEngine(
                        userID, 
                        userPassword,
                        dictionary.getString("newPassword")
                    ).handleRequest();
                case GET_EVENT:
                    filters = new HashMap<String, Object>();
                    for(String key : dictionary.keySet())
                    {
                        filters.put(key, dictionary.get(key));
                    }
                    return new GetEventEngine(
                        filters
                    ).handleRequest();
                case GET_USER_DATA:
                    return new GetUserDataEngine(
                        userID, 
                        userPassword, 
                        dictionary.getString("target")
                    ).handleRequest();
                case GET_VOLUNTARIES_FOR_VISIT:
                    filters = new HashMap<String, Object>();
                    return new GetVoluntariesForVisitEngine(
                        userID, 
                        userPassword,
                        dictionary.getString("eventName"),
                        filters
                    ).handleRequest();
                case SET_CLOSED_DAYS:
                    return new SetClosedDaysEngine(
                        userID, 
                        userPassword,
                        dictionary.getInt("startDate"),
                        dictionary.getInt("endDate"),
                        dictionary.getString("organization")
                    ).handleRequest();
                case SET_NEW_EVENT:
                    return new SetNewEventEngine(
                        userID,
                        userPassword,
                        dictionary.getString("eventName"),
                        dictionary.getString("description"),
                        dictionary.getString("city"),
                        dictionary.getString("address"),
                        dictionary.getInt("startDate"),
                        dictionary.getInt("endDate"),
                        dictionary.getString("organization"),
                        dictionary.getInt("minimumUsers"),
                        dictionary.getInt("maximumUsers"),
                        dictionary.getInt("maximumFriends"),
                        dictionary.getString("visitType")
                    ).handleRequest();
                case SET_NEW_ORGANIZATION:
                    JSONArray territoriesArray = dictionary.getJSONArray("territori");
                    ArrayList<String> territories = new ArrayList<>();
                    territoriesArray.forEach(item -> territories.add((String)item));
                    return new SetNewOrganizationEngine(
                        userID,
                        userPassword,
                        dictionary.getString("organizationName"),
                        territories
                    ).handleRequest();
                case SET_NEW_USER:
                    return new SetNewUserEngine(
                        userID,
                        userPassword,
                        dictionary.getString("userName"),
                        dictionary.getString("newPassword"),
                        dictionary.getString("cityOfResidence"),
                        dictionary.getInt("birthYear")
                    ).handleRequest();
            }
        } catch(Exception e)
        {
            return "ERROR";
        }
        return "";
    }

    public synchronized void run()
    {
        handleUserRequest();
    }
}

