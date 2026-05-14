package jchat.client;

import jchat.core.net.entity.JChatTextMessage;
import jchat.core.net.entity.JChatUserCredentials;
import jchat.core.net.protocol.JChatProtocolUtil;
import jchat.core.net.protocol.tcp.*;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Map;

public class JChatClient
{
    private SocketChannel socket;
    private boolean isRunning;
    private Selector selector;
    private Thread clientThread;
    private JChatUserCredentials credentials;
    private boolean isLoggedIn;
    private boolean doRegistrationFirst;


    public JChatClient(JChatUserCredentials credentials)
    {
        this.credentials = credentials;
        this.isRunning = false;
        this.doRegistrationFirst = false;
        this.isLoggedIn = false;
    }

    public JChatClient(JChatUserCredentials credentials, boolean doRegistrationFirst)
    {
        this(credentials);
        this.doRegistrationFirst = doRegistrationFirst;
    }


    public String getUsername()
    {
        return credentials.username();
    }

    public void start(String host, int port)
    {
        isRunning = true;
        clientThread = new Thread(() ->
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
        } catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

    public void sendMessage(String message)
    {
        try
        {
            JChatProtocolUtil.sendJChatTCPPacket(
                    JChatClientMessagePacket.create(
                            new JChatTextMessage(credentials.username(), message)), socket);
        } catch (IOException ex)
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

            if (socket.isConnected()) System.out.println("Client Socket connected");
            socket.configureBlocking(false);
            socket.register(selector, SelectionKey.OP_READ);

            if(doRegistrationFirst)
                sendAccountRegistrationRequest(credentials.username(), credentials.password());
            else
                sendLoginRequest();

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void pollServerResponses()
    {
        while (isRunning)
        {
            try
            {
                selector.select();

                for (SelectionKey key : selector.selectedKeys())
                {
                    if (key.isReadable())
                    {
                        readPackets((SocketChannel) key.channel());
                    }
                }
                selector.selectedKeys().clear();
            }
            catch (Exception ex) {
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
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void readPackets(SocketChannel socketChannel)
    {
        try
        {
            for (JChatTCPPacket packet : JChatProtocolUtil.readJChatTCPPacket(socketChannel)) {
                switch (packet.getPacketCode()) {
                    case SERVER_GENERATED_MESSAGE:
                    case CLIENT_GENERATED_MESSAGE:
                        onTextMessageReceived(packet);
                        break;

                    case CLIENT_CONNECTION_ACCEPTED_RESPONSE:
                        onLoginAccepted();
                        break;

                    case CLIENT_CONNECTION_REJECTED_RESPONSE:
                        onLoginRejected();
                        break;

                    case USER_ACCOUNT_REGISTRATION_SUCCESSFUL:
                        onAccountRegistrationSuccessful();
                        break;

                    case USER_ACCOUNT_REGISTRATION_FAILED:
                        onAccountRegistrationFailed(packet);
                        break;
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void sendLoginRequest() throws IOException
    {
        JChatProtocolUtil.sendJChatTCPPacket(
                JChatClientLoginRequestPacket.create(credentials.username(), credentials.password()),
                socket);
    }

    private void sendAccountRegistrationRequest(String username, String password)
    {
        try
        {
            JChatProtocolUtil.sendJChatTCPPacket(JChatUserRegistrationRequestPacket.create(username, password), socket);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void onTextMessageReceived(JChatTCPPacket packet)
    {
        JChatTextMessage message = JChatClientMessagePacket.parseTextMessage(packet);

        ClientApp.instance()
                .getClientWindow()
                .getChatRoomUI()
                .addMessage(String.format("<%s> %s", message.getSender(), message.getMessage()));
    }

    private void onLoginAccepted()
    {
        this.isLoggedIn = true;
        System.out.println("[Client INFO]: Login Successful!");
        ClientApp.instance()
                .getClientWindow()
                .showChatRoomUI();
    }

    private void onLoginRejected() throws IOException
    {
        System.out.println("[Client INFO]: Failed to log in to server. Invalid credentials.");
        ClientApp.instance()
                .getClientWindow()
                .getLoginPage()
                .displayLoginRejected();

        socket.close();
    }

    private void onAccountRegistrationSuccessful()
    {
        ClientApp.instance().getClientWindow().showLoginPage();
        ClientApp.instance().getClientWindow().getLoginPage().displayAccountCreationSuccessful();
    }

    private void onAccountRegistrationFailed(JChatTCPPacket packet)
    {
       String failureReason = JChatUserRegistrationFailedPacket.parseFailureReason(packet);
       ClientApp.instance()
               .getClientWindow()
               .getRegistrationPage()
               .displayRegistrationFailure(failureReason);
        stop();
        System.out.printf("[Client INFO]: Failed to register new account because: \"%s\"\n", failureReason);
    }
}
