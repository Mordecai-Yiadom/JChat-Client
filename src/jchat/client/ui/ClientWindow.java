package jchat.client.ui;

import javax.swing.*;
import java.awt.*;

public class ClientWindow extends JFrame
{
    private static final String DEFAULT_WINDOW_TITLE = "JChat Client";

    private ChatRoomUI chatRoomUI;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;

    private JChatUIScreen CURRENT_SCREEN;

    public ClientWindow()
    {
        super();
        setSize(900, 700);
        setPreferredSize(new Dimension(900, 700));
        setResizable(true);
        setTitle(DEFAULT_WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.chatRoomUI = new ChatRoomUI();
        this.loginPage = new LoginPage();
        this.registrationPage = new RegistrationPage();

        showLoginPage();

        setVisible(true);
    }

    public ChatRoomUI getChatRoomUI()
    {
        return chatRoomUI;
    }

    public LoginPage getLoginPage()
    {
        return loginPage;
    }

    public RegistrationPage getRegistrationPage()
    {
        return registrationPage;
    }

    private void setUIScreen(JChatUIScreen screen)
    {
        CURRENT_SCREEN = screen;
        setContentPane(CURRENT_SCREEN.root());
        repaint();
        revalidate();
    }

    public void showLoginPage()
    {
        setUIScreen(loginPage);
    }

    public void showChatRoomUI()
    {
        setUIScreen(chatRoomUI);
    }

    public void showRegistrationPage()
    {
        setUIScreen(registrationPage);
    }

}
