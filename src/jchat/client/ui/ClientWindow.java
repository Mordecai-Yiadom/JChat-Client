package jchat.client.ui;

import javax.swing.*;

public class ClientWindow extends JFrame
{
    private static final String DEFAULT_WINDOW_TITLE = "JChat Client";
    public ClientWindow()
    {
        super();
        setSize(900, 700);
        setResizable(false);
        setVisible(true);
        setTitle(DEFAULT_WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }



}
