package jchat.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatInputTextField extends JTextField
{

    public ChatInputTextField()
    {
        super();
        setBackground(JChatUIConstants.BACKGROUND_COLOR);
        setForeground(Color.WHITE);
        setCaretColor(Color.WHITE);

        setFocusable(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        setSize(500, 50);

        setFont(new Font("Segoe UI", Font.BOLD, 20));
    }
}
