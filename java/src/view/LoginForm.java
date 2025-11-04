package view;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signUpButton;

    public LoginForm() {
        setTitle("Hotel Management System - Login");
        setSize(900, 540);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background panel with image
        ImageIcon bgIcon = new ImageIcon("resources/images/login_bg.png");
        Image scaledImage = bgIcon.getImage().getScaledInstance(900, 540, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(scaledImage));
        bgLabel.setBounds(0, 0, 900, 540);
        add(bgLabel);

        // Transparent panel on top for form controls
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setOpaque(false); // transparent
        formPanel.setBounds(470, 200, 350, 300); // aligned with image

        JLabel titleLabel = new JLabel("Welcome to Paradise Hotel");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setBounds(0, -60, 350, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(0, 20, 100, 30);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(110, 20, 200, 30);
        formPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(0, 70, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(110, 70, 200, 30);
        formPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(0, 130, 150, 40);
        loginButton.setBackground(new Color(0, 123, 255)); // blue
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(160, 130, 150, 40);
        signUpButton.setBackground(new Color(40, 167, 69)); // green
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(signUpButton);

        // Add panel to background label
        bgLabel.add(formPanel);

        // Action Listeners
        loginButton.addActionListener(new LoginAction());
        signUpButton.addActionListener(e -> {
            dispose();
            new SignUpForm().setVisible(true);
        });
    }

    private class LoginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            UserDAO userDAO = new UserDAO();
            User user = userDAO.loginUser(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(null, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close login form

                // Redirect based on role
                if (user.getRole().equalsIgnoreCase("Admin")) {
                    new AdminDashboard(user).setVisible(true);
                } else if (user.getRole().equalsIgnoreCase("Receptionist")) {
                    new ReceptionistDashboard(user).setVisible(true);
                } else if (user.getRole().equalsIgnoreCase("Accountant")) {
                    new AccountantDashboard(user).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Unknown role! Contact administrator.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Inner class for SignUp Action
    private class SignUpAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new SignUpForm().setVisible(true); // Open the sign-up form
        }
    }

    // Main method to run login page directly
    public static void main(String[] args) {
        new LoginForm().setVisible(true);
    }
}