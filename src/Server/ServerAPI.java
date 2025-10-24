package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import org.json.JSONObject;


import RequestReply.ComunicationType.*;
import RequestReply.Reply.*;
import Server.Engine.*;
import Server.Engine.LoginEngine;

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


    public static void main(String [] args) 
    {
        System.out.println(userResponse())
    }


    public static final String userResponse(String request) 
    {
        JSONObject jsonObject = new JSONObject(request);
        String userID = (String)jsonObject.get("userID");
        String userPassword = (String)jsonObject.get("userPassword");
        LoginEngine login = new LoginEngine(userID, userPassword);
        if(!login.canLogIn())
        {
            return new LoginReply(false).toJSONString();
        }
        String requestType = (String)jsonObject.get("requestType");
        String requestBody = (String)jsonObject.get("requestBody");
        return requestType;
    }

    public synchronized void run()
    {
        handleUserRequest();
    }
}

