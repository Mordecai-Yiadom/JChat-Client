package jchat.client;

import java.io.*;
import java.net.*;

public class JChatClient
{
    private Socket socket;


    public JChatClient(String host, int port)
    {
        try
        {
            System.out.printf("Client Connecting on %s:%d ...\n", host, port);
            socket = new Socket(host, port);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


    }


    public void sendMessage(String message)
    {
        try
        {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(message);

            socket.getOutputStream().flush();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public String receiveMessage()
    {
        String message = null;
        try
        {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            message = (String) inputStream.readObject();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return message;
    }


}
