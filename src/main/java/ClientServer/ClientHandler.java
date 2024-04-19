package ClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import DAOs.BlockDaoInterface;
import DAOs.MySqlBlockDao;
import DTOs.Block;
import Exceptions.DaoException;

/**
 *  Written by Jakub Polacek on 13-14.4. 2024
 *  Used sample code from class as reference:
 *  github.com/logued/oop-client-server-multithreaded-2024
 */

public class ClientHandler implements Runnable
{
    BufferedReader clientReader;
    PrintWriter clientWriter;
    Socket clientSocket;
    final int clientNumber;

    public ClientHandler(Socket clientSocket, int clientNumber)
    {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNumber;
        try
        {
            this.clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        String request;
        BlockDaoInterface IBlockDao = new MySqlBlockDao();
        try
        {
            while ((request = clientReader.readLine()) != null)
            {
                System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);

                if (request.startsWith("F9"))
                {
                    String message = request.substring(2);
                    String blockAsJson = IBlockDao.blockToJson(Integer.parseInt(message));
                    clientWriter.println(blockAsJson);
                    System.out.println("Server message: JSON string of Block by id " + message + " sent to client.");
                }
                if (request.startsWith("F10")) //Feature 10 - Hannah
                {
                    String blocksAsJson = IBlockDao.allBlocksToJson();
                    clientWriter.println(blocksAsJson);
                    System.out.println("Server message: List of all blocks in database sent to client.");
                }
                else if (request.startsWith("quit"))
                {
                    clientWriter.println("Sorry to see you leaving. Goodbye.");
                    System.out.println("Server message: Quit request from client, executed.");
                }
                else
                {
                    clientWriter.println("Error - invalid command");
                    System.out.println("Server message: Invalid request from client.");
                }
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        catch (DaoException e) {
            System.out.println("Server message: Failed to access database.");
            e.printStackTrace();
        }
        finally
        {
            this.clientWriter.close();
            try
            {
                this.clientReader.close();
                this.clientSocket.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
    }
}