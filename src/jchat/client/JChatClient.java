package jchat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class JChatClient
{
    private Socket socket;
    private static final String CONNECTION_STRING = "ec2-100-54-199-217.compute-1.amazonaws.com";

    public JChatClient(String host, int port)
    {
        try
        {
            socket = new Socket(host, port);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public JChatClient()
    {
        this(CONNECTION_STRING, 2005);
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
