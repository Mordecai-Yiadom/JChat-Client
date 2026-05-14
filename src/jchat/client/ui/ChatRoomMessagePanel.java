package jchat.client.ui;

import javax.swing.*;
import java.awt.*;

public class ChatRoomMessagePanel extends JLabel
{
    public ChatRoomMessagePanel(Type type, String message)
    {
        super();
        setText(message);
        setSize(1000, 1000);
        setPreferredSize(new Dimension(1000, 100));

        switch(type)
        {
            case USER_MESSAGE:
                setFont(new Font("Segoe UI", Font.BOLD, 24));
                break;

            case SEVER_MESSAGE:
                setFont(new Font("Segoe UI", Font.ITALIC, 24));
                setForeground(new Color(42, 82, 175));
                break;
        }

        setVisible(true);
    }

    public enum Type
    {
        USER_MESSAGE,
        SEVER_MESSAGE
    }
}
