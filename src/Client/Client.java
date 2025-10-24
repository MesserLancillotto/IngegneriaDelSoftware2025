package Client;

import java.io.*;
import java.net.*;

public final class Client 
{
    private static ServerSocket serverSocket = null;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;

    private static final int PORT = 8000;
    private static final String SERVER_ADDR = "127.0.0.1";

    public static void main(String [] args)
    {
        String req = """
        {"userID":""lancillotto", "userPassword":"password"}
        """;
        System.out.println(makeServerRequest(SERVER_ADDR, PORT, req));
    }

    public static final String makeServerRequest(String server_addr, int port, String request) 
    {
        String response = "";
        try
        (
            Socket socket = new Socket(server_addr, port);
        )
        {
            System.out.println("Connected");
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
    }
}