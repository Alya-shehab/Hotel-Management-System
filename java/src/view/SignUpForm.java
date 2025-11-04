package view;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpForm extends JFrame {

    // Declare components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton, backButton;
    private JLabel titleLabel;

    public SignUpForm() {
        // Frame settings
        setTitle("Hotel Management System - Sign Up");
        setSize(900, 540); // match LoginForm
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setLayout(null);

        // 🌆 Background image setup
        ImageIcon bgIcon = new ImageIcon("resources/images/login_bg.png");
        Image scaledImage = bgIcon.getImage().getScaledInstance(900, 540, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(scaledImage));
        bgLabel.setBounds(0, 0, 900, 540);
        add(bgLabel);

        // 🌟 Transparent form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setOpaque(false);
        formPanel.setBounds(475, 212, 320, 350); // nicely under the bell

        // Title Label
        titleLabel = new JLabel("Create Your Account");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setBounds(0, -10, 320, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(titleLabel);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(0, 30, 100, 30);
        formPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(110, 30, 200, 30);
        formPanel.add(usernameField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(0, 80, 100, 30);
        formPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(110, 80, 200, 30);
        formPanel.add(passwordField);

        // Role
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(0, 130, 100, 30);
        formPanel.add(roleLabel);

        String[] roles = {"Admin", "Receptionist", "Accountant"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(110, 130, 200, 30);
        formPanel.add(roleComboBox);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.setBounds(0, 190, 140, 40);
        registerButton.setBackground(new Color(60, 179, 113)); // green
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(registerButton);

        // Back Button
        backButton = new JButton("Back to Login");
        backButton.setBounds(160, 190, 150, 40);
        backButton.setBackground(new Color(100, 149, 237)); // blue
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(backButton);

        bgLabel.add(formPanel);

        // Button Actions
        registerButton.addActionListener(new RegisterAction());
        backButton.addActionListener(new BackAction());
    }

    // Inner class for Register Action
    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword()).trim();
            String role = roleComboBox.getSelectedItem().toString();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UserDAO userDAO = new UserDAO();
            int userId = (int)(Math.random() * 10000); // random ID

            User newUser = new User(userId, username, password, role);
            boolean success = userDAO.registerUser(newUser);

            if (success) {
                JOptionPane.showMessageDialog(null, "Registration Successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new LoginForm().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Registration Failed! Try a different username.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Inner class for Back Action
    private class BackAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new LoginForm().setVisible(true);
        }
    }

    // Main method to run signup page directly
    public static void main(String[] args) {
        new SignUpForm().setVisible(true);
    }
}
