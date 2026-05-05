package jchat.client;

import jchat.client.ui.ClientWindow;
import jchat.core.ConfigFileParser;

public class ClientApp
{
    private static JChatClient client;
    private static ClientWindow clientWindow;



    public static void main(String[] args)
    {
        System.out.println("Running JChat Client");

        clientWindow = new ClientWindow();
        ConfigFileParser parser = new ConfigFileParser(".env");

        client = new JChatClient(parser.getString("Remote-Host"),
                parser.getInteger("Remote-Port"));

        client.sendMessage("WHATS GOOD BRO!!!!");


    }


}
