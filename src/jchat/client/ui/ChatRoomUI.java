package jchat.client.ui;

import jchat.client.ClientApp;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.awt.*;

public class ChatRoomUI implements JChatUIScreen
{
    private JPanel rootPanel;
    private JTextField messageTextField;
    private JButton submitMessageButton;
    private JScrollPane textMessageScrollPane;
    private JPanel panel;
    private JPanel bottomPanel;
    private JPanel messagePanel;

    public ChatRoomUI()
    {
        super();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

        submitMessageButton.addActionListener((event)->
        {
            ClientApp.instance().getChatClient().sendMessage(messageTextField.getText());

            addMessage(String.format(String.format("<%s> %s",
                    ClientApp.instance().getChatClient().getUsername(),
                    messageTextField.getText())));

            messageTextField.setText(null);
        });

    }

    @Override
    public Container root()
    {
        return rootPanel;
    }


    public void addMessage(String message)
    {
        messagePanel.add(new ChatRoomMessagePanel(message));
        messagePanel.repaint();
        messagePanel.revalidate();
    }


}
