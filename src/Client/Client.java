package Client;

import java.io.*;
import java.net.*;
import java.util.*;

import RequestReply.Request.*;
import RequestReply.ComunicationType.*;
import RequestReply.UserRoleTitle.*;

public final class Client 
{
    private static ServerSocket serverSocket = null;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;

    private static final int PORT = 8000;
    private static final String SERVER_ADDR = "127.0.0.1";

    public static final String loginRequest(String userID, String userPassword)
    {
        LoginRequest requestBody = new LoginRequest();
        Request request = new Request(ComunicationType.LOGIN, userID, userPassword, requestBody);
        return request.toJSONString();
    }

    public static final String passwordChange(String userID, String userPassword, String newPassword)
    {
        PasswordChangeRequest requestBody = new PasswordChangeRequest(newPassword);
        Request request = new Request(ComunicationType.PASSWORD_CHANGE, userID, userPassword, requestBody);
        return request.toJSONString();
    }

    public static final String newOrganization(
        String userID, 
        String userPassword, 
        String organizationName, 
        ArrayList<String> territoriesOfCompetence
    ) {
        NewOrganizationRequest requestBody = new NewOrganizationRequest(organizationName, territoriesOfCompetence);
        Request request = new Request(ComunicationType.NEW_ORGANIZATION, userID, userPassword, requestBody);
        return request.toJSONString();
    }
    
    public static final String newUser(
        String tmpID,
        String tmpPassword,
        String userName,
        String userNewPassword, 
        String cityOfResidence,
        Integer birthYear,
        UserRoleTitle role 
    ) {
        NewUserRequest requestBody = new NewUserRequest(
            userName,
            userNewPassword,
            cityOfResidence,
            birthYear,
            role);
        Request request = new Request(ComunicationType.NEW_USER, tmpID, tmpPassword, requestBody);
        return request.toJSONString();
    }

    public static final String makeServerRequest(String server_addr, int port, String request) 
    {
        String response = "";
        try
        (
            Socket socket = new Socket(server_addr, port);
        )
        {
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

    public static void main(String [] args) 
    {

        String userName = "Lucia Michelini";
        String newPassword = "sicurissimaAlQuadrato";
        String cityOfResidence = "Como";
        int birthYear = 2000;
        UserRoleTitle role = UserRoleTitle.VOLUNTARY;
        String request = passwordChange("VOLUNTARY.Lucia.Michelini.0", "sicurissimaAlQuadrato", "sicurissimaAlCubo3");

        System.out.println("Request: ");
        System.out.println(request);
        System.out.println("Response: ");
        System.out.println(makeServerRequest(SERVER_ADDR, PORT, request));
    }
}