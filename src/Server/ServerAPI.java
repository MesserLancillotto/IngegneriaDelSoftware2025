package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;
import java.util.concurrent.*;

import RequestReply.Reply.*;
import RequestReply.Request.*;
import RequestReply.ComunicationType.*;
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

            System.out.println(dataInputStream);
            
            String request = dataInputStream.readUTF();
            String response = userResponse(request);

            System.out.println("Richiesta: ");
            System.out.println(request);

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
        String user = (String)dictionary.getString("userID");
        String password = (String)dictionary.getString("userPassword");
        LoginEngine engine = new LoginEngine(user, password);
        boolean canLogIn = false;
        try
        {
            canLogIn = engine.canLogIn();
            if(canLogIn)
            {
                switch(
                    ComunicationTypeStringConverter.stringToComunicationType(
                        (String)dictionary.getString("requestType"))
                ) {
                    case LOGIN:
                        return new LoginReply(canLogIn).toJSONString();
                    case NEW_ORGANIZATION:
                        String orgName = (String)dictionary.getString("organizationName");
                        ArrayList<String> list = new ArrayList<String>();
                        for(int i = 0; 
                        i < dictionary.getJSONArray("territoriesOfCompetence").length();
                        i++
                        ) {
                            String e = dictionary.getJSONArray("territoriesOfCompetence").getString(i);
                            list.add(e);
                        }
                        String resp = new NewOrganizationEngine(orgName, list).handleRequest(); 
                        return resp;
                }
            }
            // NewOrganizationEngine orgEngine = new NewOrganizationEngine();

        } catch(Exception e)
        {
            canLogIn = false;
        }
        return "";
    }

    public synchronized void run()
    {
        handleUserRequest();
    }
}

