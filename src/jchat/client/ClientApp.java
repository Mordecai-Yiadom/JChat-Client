package jchat.client;

import jchat.client.ui.ClientWindow;
import jchat.core.net.entity.JChatUserCredentials;
import jchat.core.util.ConfigFileParser;
import java.util.Scanner;

public class ClientApp
{
    private static ClientApp INSTANCE;

    private JChatClient client;
    private ClientWindow clientWindow;

    private ClientApp()
    {
        clientWindow = new ClientWindow();
        System.out.println("[Client INFO]: Starting JChat UI");
    }

    public static ClientApp instance()
    {
        if(INSTANCE == null)
            INSTANCE = new ClientApp();
        return INSTANCE;
    }



    public void start(String username, String password)
    {
        System.out.println("[Client INFO]: Starting JChat Net Client");

        ConfigFileParser parser = new ConfigFileParser(".env");

        client = new JChatClient(new JChatUserCredentials(username, password));

        client.start(parser.getString("Remote-Host"),
                parser.getInteger("Remote-Port"));
    }

    public void start(String username, String password, boolean doRegistrationFirst)
    {
        System.out.println("[Client INFO]: Starting JChat Net Client");

        ConfigFileParser parser = new ConfigFileParser(".env");

        client = new JChatClient(new JChatUserCredentials(username, password), doRegistrationFirst);

        client.start(parser.getString("Remote-Host"),
                parser.getInteger("Remote-Port"));
    }


    public ClientWindow getClientWindow() {

        return clientWindow;
    }

    public JChatClient getChatClient()
    {
        return client;
    }
}
