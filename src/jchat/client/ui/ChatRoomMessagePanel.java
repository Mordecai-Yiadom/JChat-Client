package jchat.client.ui;

import javax.swing.*;
import java.awt.*;

public class ChatRoomMessagePanel extends JLabel
{
    public ChatRoomMessagePanel(String message)
    {
        super();
        setText(message);
        setSize(1000, 1000);
        setPreferredSize(new Dimension(1000, 100));
        setFont(new Font("Segoe UI", Font.BOLD, 24));
        setVisible(true);
    }
}
