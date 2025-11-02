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
}