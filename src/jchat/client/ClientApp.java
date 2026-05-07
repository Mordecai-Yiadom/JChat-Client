package jchat.client;

import jchat.client.ui.ClientWindow;
import jchat.core.ConfigFileParser;

import java.util.Scanner;

public class ClientApp
{
    private static JChatClient client;
    private static ClientWindow clientWindow;



    public static void main(String[] args)
    {
        System.out.println("Running JChat Client");

        //clientWindow = new ClientWindow();
        ConfigFileParser parser = new ConfigFileParser(".env");

        client = new JChatClient();

        client.start(parser.getString("Remote-Host"),
                parser.getInteger("Remote-Port"));

        Scanner scanner = new Scanner(System.in);

        while(client.isRunning())
        {
            if(scanner.hasNextLine())
                client.sendMessage(scanner.nextLine());
        }

        client.waitForExit();
    }


}
