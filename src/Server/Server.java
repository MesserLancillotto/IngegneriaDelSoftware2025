package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server
{
    public static void main(String [] args) 
    {
            ServerAPI api = new ServerAPI();
            api.start(); 
    /*
        while(true)
        {
            ServerAPI api = new ServerAPI();
            api.start(); 
            try {
                api.join();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e);
            }
        }
    */
    }
}
