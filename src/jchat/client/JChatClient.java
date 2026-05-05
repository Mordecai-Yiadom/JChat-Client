package jchat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class JChatClient
{
    private Socket socket;


    public JChatClient(String host, int port)
    {
        try
        {
            System.out.println(String.format("Client Connecting on %s:%d ...\n", host, port));
            socket = new Socket(host, port);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


    }


    public void connectToHost()
    {
        try
        {
            socket.connect(null);
        }
        catch (IOException e)
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


}
