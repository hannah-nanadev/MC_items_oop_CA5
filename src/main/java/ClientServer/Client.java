package ClientServer;

/**
 *  Written by Jakub Polacek on 13-14.4. 2024
 *  Used sample code from class as reference:
 *  github.com/logued/oop-client-server-multithreaded-2024
 */

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import MainApp.MainApp;
import com.sun.tools.javac.Main;

public class Client
{
    public static void main(String[] args)
    {
        Client client = new Client();
        client.start();
    }
    public void start()
    {
        try (Socket socket = new Socket("localhost", 7420);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            System.out.println("Client message: The Client is running and has connected to the server");
            Scanner keyboardInput = new Scanner(System.in);
            System.out.println("Please enter a command: ");
            String clientCommand = keyboardInput.nextLine();

            while(true)
            {
                out.println(clientCommand);
                if (clientCommand.startsWith("test"))
                {
                    String response = in.readLine();
                    System.out.println("Client message: Response from server: \"" + response + "\"");
                }
                else if (clientCommand.startsWith("quit"))
                {
                    String response = in.readLine();
                    System.out.println("Client message: Response from server: \"" + response + "\"");
                    break;
                }
                else
                {
                    System.out.println("Command unknown. Try again.");
                }

                keyboardInput = new Scanner(System.in);
                System.out.println("Please enter a command: ");
                clientCommand = keyboardInput.nextLine();
            }
        }
        catch (IOException e)
        {
            System.out.println("Client message: IOException: " + e);
        }
        System.out.println("Exiting client, server may still be running.");
    }
}
