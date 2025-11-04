package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountantDashboard extends JFrame {

    private User accountantUser;
    private JLabel welcomeLabel;
    private JButton manageInvoicesButton, logoutButton;

    public AccountantDashboard(User user) {
        this.accountantUser = user;

        // Frame settings
        setTitle("Hotel Management System - Accountant Dashboard");
        setSize(900, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background Image
        JLabel background = new JLabel(new ImageIcon(
                new ImageIcon("resources/images/admin_db.png").getImage().getScaledInstance(900, 540, Image.SCALE_SMOOTH)));
        background.setBounds(0, 0, 900, 540);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 900, 540);
        add(layeredPane);

        // Title Label
        JLabel titleLabel = new JLabel("Accountant Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(60, 30, 20));
        titleLabel.setBounds(0, 20, 900, 40);
        layeredPane.add(titleLabel, JLayeredPane.PALETTE_LAYER);

        // Welcome Label
        welcomeLabel = new JLabel("Welcome, " + accountantUser.getUsername(), JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(30, 30, 30));
        welcomeLabel.setBounds(0, 70, 900, 30);
        layeredPane.add(welcomeLabel, JLayeredPane.PALETTE_LAYER);

        // Manage Invoices Button
        manageInvoicesButton = themedButton("Manage Invoices", "resources/images/invoice.png", new Color(255, 140, 0));
        manageInvoicesButton.setBounds(340, 160, 210, 75);
        manageInvoicesButton.addActionListener(e -> new InvoiceManagementForm().setVisible(true));
        layeredPane.add(manageInvoicesButton, JLayeredPane.PALETTE_LAYER);

        // Logout Button
        logoutButton = themedButton("Logout", "resources/images/logout.png", new Color(220, 20, 60));
        logoutButton.setBounds(365, 260, 150, 75);
        logoutButton.addActionListener(new LogoutAction());
        layeredPane.add(logoutButton, JLayeredPane.PALETTE_LAYER);

        // Background last
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
    }

    private JButton themedButton(String text, String iconPath, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Load and scale icon
        ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        button.setIcon(icon);

        // Center everything
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);

        return button;
    }

    private class LogoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new LoginForm().setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        User dummyAccountant = new User(3, "accountantUser", "accountantPass", "Accountant");
        new AccountantDashboard(dummyAccountant).setVisible(true);
    }
}
