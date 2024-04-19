package ClientServer;

/**
 *  Written by Jakub Polacek on 13-14.4. 2024
 *  Used sample code from class as reference:
 *  github.com/logued/oop-client-server-multithreaded-2024
 */

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import DAOs.MySqlBlockDao;
import com.google.gson.Gson;
import DTOs.Block;

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
            System.out.println("Available commands: F9, F10 quit");
            String clientCommand = keyboardInput.nextLine();

            while(true)
            {
                if (clientCommand.equals("F9"))
                {
                    System.out.println("Function 9 selected - Display Block by ID");
                    System.out.println("Please input the desired ID to be found:");
                    clientCommand = keyboardInput.nextLine();
                    out.println("F9"+clientCommand);
                    String response = in.readLine();
                    Gson gsonParser = new Gson();
                    Block block = gsonParser.fromJson(response,Block.class);
                    //TODO make output nicer
                    System.out.println(block.toString());
                }
                else if (clientCommand.equals("F10")) //Feature 10 - Hannah
                {
                    System.out.println("Function 10 selected - Display All Blocks");
                    out.println("F10");
                    String response = in.readLine();
                    //TODO convert back to list, improve formatting

                    Gson parse = new Gson();
                    List allBlocks = parse.fromJson(response, List.class); //TODO TEST THIS AND MAKE IT MAYBE LESS SCUFFED (AND IMPLEMENT IT PROPERLY)

                    System.out.println(allBlocks.toString());
                }
                else if (clientCommand.startsWith("quit"))
                {
                    out.println(clientCommand);
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
