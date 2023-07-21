package view;

import javax.swing.*;

import controller.Controller;
import model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginPageScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private String currentUser;

    public LoginPageScreen() {
        setTitle("Login Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Email Label and Field
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = String.valueOf(passwordField.getPassword());
        
                ArrayList<User> users = Controller.getInstance().getAllUsers();
                User loggedInUser = null;
        
                for (User u : users) {
                    if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                        loggedInUser = u;
                        currentUser = loggedInUser.getName();
                        break; 
                    }
                }
        
                if (loggedInUser != null) {
                    Controller.getInstance().currentUser = loggedInUser;
                    JOptionPane.showMessageDialog(panel, "Login Success as " + loggedInUser.getName());
                    showHomePage();
                    panel.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(panel, "Login failed!");
                }
            }
        });
        

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToLandingMenu();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        gbc.gridy = 3;
        panel.add(backButton, gbc);

        add(panel);
    }

    private void showHomePage() {
        HomepageScreen homepage = new HomepageScreen(currentUser);
        homepage.setVisible(true);
    }

    private void goBackToLandingMenu() {
        LandingPageScreen landingMenu = new LandingPageScreen();
        landingMenu.setVisible(true);
        dispose(); 
    }

}
