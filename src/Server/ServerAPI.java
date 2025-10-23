package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import org.json.JSONObject;

import RequestReply.ComunicationType.*;

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

    public static final String userResponse(String request) 
    {
        JSONObject jsonObject = new JSONObject(request);
        String requestType = jsonObject.get("requestType");
        String userID = jsonObject.get("userID");
        String userPassword = jsonObject.get("userPassword");
        String requestBody = jsonObject.get("requestBody");
        return requestType;
    }

    public synchronized void run()
    {
        handleUserRequest();
    }
}

