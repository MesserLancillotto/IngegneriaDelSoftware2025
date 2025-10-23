package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import org.json.JSONObject;

class ServerAPI extends Thread
{
    private static Socket socket = null;
    private static ServerSocket serverSocket = null;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;

    private static final int PORT = 8000;

    public static void main(String [] args) 
    {
        System.out.println(userResponse("{\"name\":\"Pippo\"}"));
    }

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
        System.out.println(jsonObject);
        return "Something stringy something linky";
    }

    public synchronized void run()
    {
        handleUserRequest();
    }
}

