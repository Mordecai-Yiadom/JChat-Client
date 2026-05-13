package jchat.client.ui;

import jchat.client.ClientApp;

import javax.swing.*;
import java.awt.*;

public class LoginPage implements JChatUIScreen
{
    private JPanel root;
    private JLabel loginLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel usernameLabel;
    private JPanel passwordPanel;
    private JPanel usernamePanel;
    private JButton submitButton;
    private JLabel loginStatusLabel;

    public LoginPage()
    {
        super();

        submitButton.addActionListener((event)->
        {
            ClientApp.instance().start(usernameField.getText(), String.valueOf(passwordField.getPassword()));
        });
    }

    @Override
    public Container root()
    {
        return root;
    }

    public void displayLoginRejected()
    {
        loginStatusLabel.setText("Invalid Credentials!");
        loginStatusLabel.setForeground(Color.RED);
    }
}
