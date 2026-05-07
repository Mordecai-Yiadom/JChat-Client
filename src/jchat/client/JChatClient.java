package jchat.client;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class JChatClient
{
    private SocketChannel socket;
    private boolean isRunning;
    private Selector selector;
    private Queue<String> messageQueue;
    private Thread clientThread;

    public JChatClient()
    {
        isRunning = false;
    }

    public void start(String host, int port)
    {
        isRunning = true;
        clientThread = new Thread(()->
        {
            connectToServer(host, port);
            pollServerResponses();
        });
        clientThread.start();
    }

    public void stop()
    {
        isRunning = false;
    }

    public boolean isRunning()
    {
        return isRunning;
    }

    public void waitForExit()
    {
        try
        {
            clientThread.join();
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

    public void sendMessage(String message)
    {
        try
        {
            ByteBuffer byteBuffer = ByteBuffer.allocate(message.getBytes().length);
            byteBuffer.clear().put(message.getBytes()).flip();

            while(byteBuffer.hasRemaining())
            {
                socket.write(byteBuffer);
            }

            System.out.printf("[Client Info] Message sent to %s\n", socket.getRemoteAddress().toString());
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }



    private void connectToServer(String host, int port)
    {
        try
        {
            selector = Selector.open();
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress(host, port));

            if(socket.isConnected()) System.out.println("Client Socket connected");
            socket.configureBlocking(false);
            socket.register(selector, SelectionKey.OP_READ);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void pollServerResponses()
    {
        sendMessage("What's good paul.");
        while(isRunning)
        {
            try
            {
                selector.select();

                for(SelectionKey key : selector.selectedKeys())
                {
                    if(key.isReadable())
                    {
                        System.out.printf("<Server> %s\n", readResponse());
                    }
                }

                selector.selectedKeys().clear();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        closeConnection();
    }

    private void closeConnection()
    {
        try
        {
            socket.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private String readResponse()
    {
        StringBuilder message = new StringBuilder();

        try
        {

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.clear();
            int bytesRead = 0;

            bytesRead += socket.read(byteBuffer);


            for(int i = 0; i < byteBuffer.array().length; i++)
            {
                char c = (char) byteBuffer.array()[i];
                if(c == '\0') break;
                message.append(c);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
        return message.toString();
    }
}
