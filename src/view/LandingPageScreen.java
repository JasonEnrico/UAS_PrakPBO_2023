package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPageScreen extends JFrame {
    private JButton loginButton;
    private JButton registerButton;

    public LandingPageScreen() {
        setTitle("Todo List App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));

        loginButton = new JButton("Login Pengguna");
        registerButton = new JButton("Registrasi Pengguna Baru");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginMenu();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegistrationMenu();
            }
        });

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
    }

    private void showLoginMenu() {
        LoginPageScreen loginPage = new LoginPageScreen();
        loginPage.setVisible(true);
    }

    private void showRegistrationMenu() {
        RegistrationPageScreen registrationPage = new RegistrationPageScreen();
        registrationPage.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LandingPageScreen landingMenu = new LandingPageScreen();
            landingMenu.setVisible(true);
        });
    }
}

