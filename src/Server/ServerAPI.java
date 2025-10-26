package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;
import java.util.concurrent.*;

import RequestReply.Reply.*;
import RequestReply.Request.*;
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
        JSONObject dictionary = new JSONObject(request);
        String user = (String)dictionary.get("userID");
        String password = (String)dictionary.get("userPassword");
        LoginEngine engine = new LoginEngine(user, password);
        boolean canLogIn = false;
        try
        {
            canLogIn = engine.canLogIn();
            return new LoginReply(canLogIn).toJSONString();
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

