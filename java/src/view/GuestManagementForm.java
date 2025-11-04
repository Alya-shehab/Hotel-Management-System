package view;

import dao.GuestDAO;
import model.Guest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class GuestManagementForm extends JFrame {

    private JTextField guestIdField, nameField, contactNumberField;
    private JTextField checkInDateField, checkOutDateField;
    private JButton addButton, updateButton, deleteButton, refreshButton, backButton;
    private JTable guestTable;
    private DefaultTableModel tableModel;

    private GuestDAO guestDAO;

    public GuestManagementForm() {
        guestDAO = new GuestDAO();

        setTitle("Hotel Management System - Manage Guests");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Optional background image
        JLabel background = new JLabel(new ImageIcon(
                new ImageIcon("resources/images/admin_db.png").getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH)));
        background.setBounds(0, 0, 900, 600);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 900, 600);
        add(layeredPane);

        // Title
        JLabel titleLabel = new JLabel("Guest Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(60, 30, 20));
        titleLabel.setBounds(0, 10, 900, 40);
        layeredPane.add(titleLabel, JLayeredPane.PALETTE_LAYER);

        // Input Fields Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBounds(30, 60, 320, 180);
        inputPanel.setOpaque(false);

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        inputPanel.add(createLabeledField("Guest ID:", guestIdField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Name:", nameField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Contact No:", contactNumberField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Check-In Date:", checkInDateField = new JTextField("YYYY-MM-DD"), labelFont));
        inputPanel.add(createLabeledField("Check-Out Date:", checkOutDateField = new JTextField("YYYY-MM-DD"), labelFont));

        layeredPane.add(inputPanel, JLayeredPane.MODAL_LAYER);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBounds(30, 260, 320, 100);
        buttonPanel.setOpaque(false);

        addButton = themedButton("Add Guest", new Color(60, 179, 113));
        updateButton = themedButton("Update Guest", new Color(255, 165, 0));
        deleteButton = themedButton("Delete Guest", new Color(220, 20, 60));
        refreshButton = themedButton("Refresh", new Color(100, 149, 237));
        backButton = themedButton("Back", new Color(70, 130, 180));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        layeredPane.add(buttonPanel, JLayeredPane.MODAL_LAYER);

        // Table
        guestTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"Guest ID", "Name", "Contact No", "Check-In Date", "Check-Out Date"}, 0);
        guestTable.setModel(tableModel);
        guestTable.setRowHeight(25);
        guestTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(guestTable);
        scrollPane.setBounds(370, 60, 500, 460);
        scrollPane.setBorder(new LineBorder(Color.DARK_GRAY, 1));
        layeredPane.add(scrollPane, JLayeredPane.MODAL_LAYER);

        // Background last
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Button Actions
        addButton.addActionListener(new AddGuestAction());
        updateButton.addActionListener(new UpdateGuestAction());
        deleteButton.addActionListener(new DeleteGuestAction());
        refreshButton.addActionListener(new RefreshAction());
        backButton.addActionListener(new BackAction());

        loadGuests();
    }

    // Helper to create label + field pair for GridLayout
    private JPanel createLabeledField(String labelText, JTextField textField, Font font) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    private JButton themedButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }

    // No logic changes from here ↓↓↓
    private void loadGuests() {
        tableModel.setRowCount(0);
        List<Guest> guestList = guestDAO.getAllGuests();
        for (Guest guest : guestList) {
            tableModel.addRow(new Object[]{
                    guest.getGuestId(),
                    guest.getName(),
                    guest.getContactNumber(),
                    guest.getCheckInDate(),
                    guest.getCheckOutDate()
            });
        }
    }

    private class AddGuestAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int guestId = Integer.parseInt(guestIdField.getText().trim());
                String name = nameField.getText().trim();
                String contact = contactNumberField.getText().trim();
                LocalDate checkIn = LocalDate.parse(checkInDateField.getText().trim());
                LocalDate checkOut = LocalDate.parse(checkOutDateField.getText().trim());

                Guest guest = new Guest(guestId, name, contact, checkIn, checkOut);
                boolean success = guestDAO.addGuest(guest);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Guest added successfully!");
                    loadGuests();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add guest!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class UpdateGuestAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int guestId = Integer.parseInt(guestIdField.getText().trim());
                String name = nameField.getText().trim();
                String contact = contactNumberField.getText().trim();
                LocalDate checkIn = LocalDate.parse(checkInDateField.getText().trim());
                LocalDate checkOut = LocalDate.parse(checkOutDateField.getText().trim());

                Guest guest = new Guest(guestId, name, contact, checkIn, checkOut);
                boolean success = guestDAO.updateGuest(guest);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Guest updated successfully!");
                    loadGuests();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update guest!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class DeleteGuestAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int guestId = Integer.parseInt(guestIdField.getText().trim());
                boolean success = guestDAO.deleteGuest(guestId);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Guest deleted successfully!");
                    loadGuests();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete guest!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class RefreshAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadGuests();
        }
    }

    private class BackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new GuestManagementForm().setVisible(true);
    }
}
