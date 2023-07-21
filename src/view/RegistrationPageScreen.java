package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

public class RegistrationPageScreen extends JFrame {
    private JTextField emailField;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JDateChooser birthdayDatePicker;
    private JFileChooser photoFileChooser;

    public RegistrationPageScreen() {
        setTitle("Registrasi Pengguna Baru");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Email Label and Field
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        // Name Label and Field
        JLabel nameLabel = new JLabel("Nama:");
        nameField = new JTextField(20);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        // Gender Radio Buttons
        JLabel genderLabel = new JLabel("Jenis Kelamin:");
        maleRadioButton = new JRadioButton("Laki-laki");
        femaleRadioButton = new JRadioButton("Perempuan");
        ButtonGroup genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femaleRadioButton);

        // Birthday DatePicker
        JLabel birthdayLabel = new JLabel("Tanggal Ulang Tahun:");
        birthdayDatePicker = new JDateChooser();

        // Photo FileChooser
        JLabel photoLabel = new JLabel("Pilih Foto:");
        photoFileChooser = new JFileChooser();

        // Register Button
        JButton registerButton = new JButton("Registrasi");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String name = nameField.getText();
                char[] password = passwordField.getPassword();
                String passwordStr = new String(password);
                int gender = maleRadioButton.isSelected() ? 1 : 0;
                Date birthday = birthdayDatePicker.getDate();
                String photoPath = photoFileChooser.getSelectedFile().getPath();

                if (passwordStr.length() < 8) {
                    JOptionPane.showMessageDialog(RegistrationPageScreen.this,
                            "Password harus terdiri dari minimal 8 karakter.",
                            "Registrasi Gagal", JOptionPane.ERROR_MESSAGE);
                } else {
                    saveUserDataToDatabase(email, name, passwordStr, gender, birthday, photoPath);

                    JOptionPane.showMessageDialog(RegistrationPageScreen.this,
                            "Registrasi berhasil. Data berhasil disimpan.",
                            "Registrasi Berhasil", JOptionPane.INFORMATION_MESSAGE);
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
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(genderLabel, gbc);

        gbc.gridx = 1;
        panel.add(maleRadioButton, gbc);

        gbc.gridx = 2;
        panel.add(femaleRadioButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(birthdayLabel, gbc);

        gbc.gridx = 1;
        panel.add(birthdayDatePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(photoLabel, gbc);

        gbc.gridx = 1;
        panel.add(photoFileChooser, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(480, 250));
        add(scrollPane);
    }

    private void saveUserDataToDatabase(String email, String name, String password,
            int gender, Date birthday, String photoPath) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String databaseURL = "jdbc:mysql://localhost:3306/uas";
            String username = "root";
            String pass = "";

            conn = DriverManager.getConnection(databaseURL, username, pass);

            String query = "INSERT INTO user (email, name, password, gender, birthday, photo) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, name);
            stmt.setString(3, pass);
            stmt.setInt(4, gender);
            stmt.setDate(5, new java.sql.Date(birthday.getTime()));
            stmt.setString(6, photoPath);

            // Execute the insert query
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(RegistrationPageScreen.this,
                        "Registrasi berhasil. Data berhasil disimpan.",
                        "Registrasi Berhasil", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(RegistrationPageScreen.this,
                        "Registrasi gagal. Data tidak dapat disimpan.",
                        "Registrasi Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(RegistrationPageScreen.this,
                    "Terjadi kesalahan saat menyimpan data.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void goBackToLandingMenu() {
        LandingPageScreen landingMenu = new LandingPageScreen();
        landingMenu.setVisible(true);
        dispose();
    }

}
