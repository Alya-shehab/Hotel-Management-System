package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {

    private User adminUser;
    private JLabel welcomeLabel;
    private JButton manageRoomsButton, manageGuestsButton, manageReservationsButton;
    private JButton manageInvoicesButton, manageServicesButton, manageFeedbackButton;
    private JButton logoutButton;

    public AdminDashboard(User user) {
        this.adminUser = user;

        // Frame settings
        setTitle("Hotel Management System - Admin Dashboard");
        setSize(900, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background image
        JLabel background = new JLabel();
        ImageIcon bg = new ImageIcon("resources/images/admin_db.png");
        background.setIcon(new ImageIcon(bg.getImage().getScaledInstance(900, 540, Image.SCALE_SMOOTH)));
        background.setBounds(0, 0, 900, 540);

        // Layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 900, 540);
        add(layeredPane);

        // Welcome Label
        welcomeLabel = new JLabel("HOTEL ADMIN");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(60, 30, 20));
        welcomeLabel.setBounds(350, 20, 300, 40);
        layeredPane.add(welcomeLabel, JLayeredPane.PALETTE_LAYER);

        // Button grid layout
        manageRoomsButton = createIconButton("Manage Rooms", "resources/images/rooms.png", 100, 100);
        manageGuestsButton = createIconButton("Manage Guests", "resources/images/guest.png", 350, 100);
        manageInvoicesButton = createIconButton("Manage Invoices", "resources/images/invoices.png", 600, 100);

        manageReservationsButton = createIconButton("Manage Reservations", "resources/images/reservations.png", 100, 250);
        manageServicesButton = createIconButton("Manage Services", "resources/images/hotel-service.png", 350, 250);
        manageFeedbackButton = createIconButton("Manage Feedback", "resources/images/feedback.png", 600, 250);

        logoutButton = createIconButton("Logout", "resources/images/logout.png", 740, 20);
        logoutButton.setBackground(new Color(255, 140, 0));

        // Add to layered pane
        layeredPane.add(manageRoomsButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(manageGuestsButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(manageInvoicesButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(manageReservationsButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(manageServicesButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(manageFeedbackButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(logoutButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Button actions
        manageRoomsButton.addActionListener(e -> new RoomManagementForm().setVisible(true));
        manageGuestsButton.addActionListener(e -> new GuestManagementForm().setVisible(true));
        manageReservationsButton.addActionListener(e -> new ReservationManagementForm().setVisible(true));
        manageInvoicesButton.addActionListener(e -> new InvoiceManagementForm().setVisible(true));
        manageServicesButton.addActionListener(e -> new ServiceManagementForm().setVisible(true));
        manageFeedbackButton.addActionListener(e -> new FeedbackManagementForm().setVisible(true));
        logoutButton.addActionListener(new LogoutAction());
    }

    private JButton createIconButton(String text, String iconPath, int x, int y) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setBounds(x, y, 200, 120);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setBackground(new Color(255, 255, 255, 220));
    
        // Round the corners
        button.setBorder(new util.RoundedBorder(15)); // <-- import this class
    
        // Load and resize icon (bigger)
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaled = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaled));
    
        return button;
    }
    

    // Action classes (same as before)
    private class RoomAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Open Room Management Form here.");
        }
    }

    private class GuestAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Open Guest Management Form here.");
        }
    }

    private class ReservationAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Open Reservation Management Form here.");
        }
    }

    private class InvoiceAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Open Invoice Management Form here.");
        }
    }

    private class ServiceAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Open Service Management Form here.");
        }
    }

    private class FeedbackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Open Feedback Management Form here.");
        }
    }

    private class LogoutAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new LoginForm().setVisible(true);
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        User dummyAdmin = new User(1, "adminUser", "adminPass", "Admin");
        new AdminDashboard(dummyAdmin).setVisible(true);
    }
}
