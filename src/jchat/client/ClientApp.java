package jchat.client;

import jchat.client.ui.ClientWindow;

public class ClientApp
{
    private static JChatClient client;
    private static ClientWindow clientWindow;

    public static void main(String[] args)
    {
        System.out.println("Running JChat Client");

        clientWindow = new ClientWindow();
        client = new JChatClient();

        client.sendMessage("WHATS GOOD BRO!!!!");
    }
}
