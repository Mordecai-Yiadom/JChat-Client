package jchat.client;

import jchat.client.ui.ClientWindow;
import jchat.core.net.entity.JChatUserLoginCredentials;
import jchat.core.util.ConfigFileParser;
import java.util.Scanner;

public class ClientApp
{
    private static ClientApp INSTANCE;

    private JChatClient client;
    private ClientWindow clientWindow;

    private ClientApp()
    {}

    public static ClientApp instance()
    {
        if(INSTANCE == null)
            INSTANCE = new ClientApp();
        return INSTANCE;
    }

    public void start()
    {
        System.out.println("Running JChat Client");

        clientWindow = new ClientWindow();
        ConfigFileParser parser = new ConfigFileParser(".env");

        client = new JChatClient(new JChatUserLoginCredentials(parser.getString("Username"),
                parser.getString("Password")));

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
