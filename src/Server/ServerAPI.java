package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;
import java.util.concurrent.*;

import RequestReply.Reply.*;
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
        JSONObject jsonObject = new JSONObject(request);
        Map<String, String> dictionary = jsonToMap(jsonObject);
        try
        (
            String user = dictionary.get("userID");
            String password = dictionary.get("userPassword");
        ) {
            Engine engine = new LoginEngine(user, password);
            if(engine.canLogIn())
            {
                System.out.println("OK");
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }

    public synchronized void run()
    {
        handleUserRequest();
    }
}

