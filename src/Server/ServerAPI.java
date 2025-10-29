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
        String user = dictionary.getString("userID");
        String password = dictionary.getString("userPassword");
        LoginEngine engine = new LoginEngine(user, password);
        boolean canLogIn = engine.canLogIn();
        System.out.println("CAN LOGIN: " + canLogIn);
        try
        {
            if(canLogIn)
            {
                System.out.println("Can log in: " + canLogIn);
                switch(
                    ComunicationTypeStringConverter.stringToComunicationType(
                        (String)dictionary.getString("requestType"))
                ) {
                    case LOGIN:
                        System.out.println("LOGIN");
                        return new LoginReply(canLogIn).toJSONString();
                    case NEW_ORGANIZATION:
                        System.out.println("NEW_ORGANIZATION");
                        String orgName = (String)dictionary.getString("organizationName");
                        ArrayList<String> list = new ArrayList<String>();
                        for(
                            int i = 0; 
                            i < dictionary.getJSONArray("territoriesOfCompetence").length();
                            i++
                        ) {
                            String e = dictionary.getJSONArray("territoriesOfCompetence").getString(i);
                            list.add(e);
                        }
                        return new NewOrganizationEngine(orgName, list).handleRequest(); 
                    case NEW_USER:
                        String userName = dictionary.getString("userName");
                        String newPassword = dictionary.getString("newPassword");
                        String cityOfResidence = dictionary.getString("cityOfResidence");
                        int birthYear = Integer.parseInt(dictionary.getString("birthYear"));
                        UserRoleTitle role = 
                            UserRoleTitleStringConverter.stringToRole(
                                dictionary.getString("role"));
                        return new NewUserEngine(user, password, userName, newPassword, cityOfResidence, birthYear, role).handleRequest();
                    case PASSWORD_CHANGE:
                        String userID = dictionary.getString("userID");
                        String oldPassword = dictionary.getString("userPassword");
                        String changedPassword = dictionary.getString("newPassword");
                        return new PasswordChangeEngine(userID, oldPassword, changedPassword).handleRequest();
                    case NEW_EVENT:
                        String eventName = dictionary.getString("eventName");
                        String description = dictionary.getString("description");
                        String city = dictionary.getString("city");
                        String address = dictionary.getString("address");
                        int startDate = dictionary.getInt("startDate");
                        int endDate = dictionary.getInt("endDate");
                        String organizationName = dictionary.getString("organizationName");
                        int minimumUsers = dictionary.getInt("minimumUsers");
                        int maximumUsers = dictionary.getInt("maximumUsers");
                        String visitType = dictionary.getString("visitType");
                        return new NewEventEngine(
                            eventName,
                            description,
                            city,
                            address,
                            startDate,
                            endDate,
                            organizationName,
                            minimumUsers,
                            maximumUsers,
                            visitType                       
                        ).handleRequest();
                    case GET_VOLUNTARIES_FOR_VISIT:
                }
            }
            System.out.println("Log in denied");

        } catch(Exception e)
        {
            canLogIn = false;
            e.printStackTrace();
        }
        return new NewOrganizationReply(false, false, 0).toJSONString();
    }

    public synchronized void run()
    {
        handleUserRequest();
    }
}

