package jchat.client.ui;

import jchat.client.ClientApp;

import javax.swing.*;
import java.awt.*;

public class RegistrationPage implements JChatUIScreen
{
    private JPanel root;
    private JLabel loginLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel usernameLabel;
    private JPanel passwordPanel;
    private JPanel usernamePanel;
    private JButton registerButton;
    private JLabel registrationStatusLabel;

    public RegistrationPage()
    {
        super();

        registerButton.addActionListener((event)->
        {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            ClientApp.instance().start(username, password, true);
        });
    }

    @Override
    public Container root()
    {
        return root;
    }

    public void displayRegistrationFailure(String failureReason)
    {
        registrationStatusLabel.setForeground(Color.RED);
        registrationStatusLabel.setText(failureReason);
    }
}
