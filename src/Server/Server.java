// javac -d bin src/Server/Server.java; java -cp bin Server.Server
// javac -d bin src/Server/Server.java
// java -cp bin Server.Server

package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server
{
    private static ServerSocket serverSocket = null;

    public static void main(String [] args) 
    {
        ServerAPI api = new ServerAPI();
        api.run();
    }
}

class ServerAPI extends Thread
{

    private static Socket socket = null;
    private static ServerSocket serverSocket = null;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;

    public static final int PORT = 8000;

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
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println("An error occurred: " + e);
        } 
    }

    public static final String userResponse(String request) 
    {
        return "Something stringy something linky";
    }

    public void run()
    {
        handleUserRequest();
    }
}

