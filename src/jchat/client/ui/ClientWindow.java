package jchat.client.ui;

import javax.swing.*;
import java.awt.*;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        //getContentPane().setLayout(new GridLayout(4, 4));
//        getContentPane().setBackground(JChatUIConstants.BACKGROUND_COLOR);
//        getContentPane().add(new ChatInputTextField());
//        getContentPane().add(new ChatMessageTextView());

    }



}
