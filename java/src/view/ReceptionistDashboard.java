package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceptionistDashboard extends JFrame {

    private User receptionistUser;
    private JLabel welcomeLabel;
    private JButton manageGuestsButton, manageReservationsButton, logoutButton;

    public ReceptionistDashboard(User user) {
        this.receptionistUser = user;

        // Frame settings
        setTitle("Hotel Management System - Receptionist Dashboard");
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
        JLabel titleLabel = new JLabel("Receptionist Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(60, 30, 20));
        titleLabel.setBounds(0, 20, 900, 40);
        layeredPane.add(titleLabel, JLayeredPane.PALETTE_LAYER);

        // Welcome Label
        welcomeLabel = new JLabel("Welcome, " + receptionistUser.getUsername(), JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(30, 30, 30));
        welcomeLabel.setBounds(0, 70, 900, 30);
        layeredPane.add(welcomeLabel, JLayeredPane.PALETTE_LAYER);

        // Manage Guests Button
        manageGuestsButton = themedButton("Manage Guests", "resources/images/guest.png", new Color(32, 178, 170));
        manageGuestsButton.setBounds(345, 120, 210, 70);
        manageGuestsButton.addActionListener(e -> new GuestManagementForm().setVisible(true));
        layeredPane.add(manageGuestsButton, JLayeredPane.PALETTE_LAYER);

        // Manage Reservations Button
        manageReservationsButton = themedButton("Manage Reservations", "resources/images/reservations.png", new Color(100, 149, 237));
        manageReservationsButton.setBounds(345, 220, 210, 70);
        manageReservationsButton.addActionListener(e -> new ReservationManagementForm().setVisible(true));
        layeredPane.add(manageReservationsButton, JLayeredPane.PALETTE_LAYER);

        // Logout Button
        logoutButton = themedButton("Logout", "resources/images/logout.png", new Color(220, 20, 60));
        logoutButton.setBounds(365, 340, 170, 70);
        logoutButton.addActionListener(new LogoutAction());
        layeredPane.add(logoutButton, JLayeredPane.PALETTE_LAYER);

        // Background last
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
    }

    private JButton themedButton(String text, String iconPath, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    
        // Load and scale icon
        ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        button.setIcon(icon);
    
        // Center icon and text
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM); // or CENTER if you prefer side by side
    
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
        User dummyReceptionist = new User(2, "receptionistUser", "receptionistPass", "Receptionist");
        new ReceptionistDashboard(dummyReceptionist).setVisible(true);
    }
}
