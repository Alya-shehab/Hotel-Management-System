package view;

import dao.ReservationDAO;
import model.Reservation;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class ReservationManagementForm extends JFrame {

    private JTextField reservationIdField, guestIdField, roomIdField;
    private JTextField checkInDateField, checkOutDateField, statusField;
    private JComboBox<String> userRoleComboBox;
    private JButton addButton, updateButton, cancelButton, refreshButton, backButton;
    private JTable reservationTable;
    private DefaultTableModel tableModel;

    private ReservationDAO reservationDAO;

    public ReservationManagementForm() {
        reservationDAO = new ReservationDAO();

        setTitle("Hotel Management System - Manage Reservations");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel background = new JLabel(new ImageIcon(
                new ImageIcon("resources/images/admin_db.png").getImage().getScaledInstance(950, 650, Image.SCALE_SMOOTH)));
        background.setBounds(0, 0, 950, 650);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 950, 650);
        add(layeredPane);

        JLabel titleLabel = new JLabel("Reservation Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(60, 30, 20));
        titleLabel.setBounds(0, 10, 950, 40);
        layeredPane.add(titleLabel, JLayeredPane.PALETTE_LAYER);

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBounds(30, 60, 360, 260);
        inputPanel.setOpaque(false);

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        inputPanel.add(createLabeledField("Reservation ID:", reservationIdField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Guest ID:", guestIdField = new JTextField(), labelFont));

        userRoleComboBox = new JComboBox<>(new String[]{"Admin", "Receptionist"});
        inputPanel.add(createLabeledField("Reserved By:", userRoleComboBox, labelFont));

        inputPanel.add(createLabeledField("Room ID:", roomIdField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Check-In Date:", checkInDateField = new JTextField("YYYY-MM-DD"), labelFont));
        inputPanel.add(createLabeledField("Check-Out Date:", checkOutDateField = new JTextField("YYYY-MM-DD"), labelFont));
        inputPanel.add(createLabeledField("Status:", statusField = new JTextField("Confirmed"), labelFont));

        layeredPane.add(inputPanel, JLayeredPane.MODAL_LAYER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBounds(30, 340, 360, 100);
        buttonPanel.setOpaque(false);

        addButton = themedButton("Add Reservation", new Color(60, 179, 113));
        updateButton = themedButton("Update Reservation", new Color(255, 165, 0));
        cancelButton = themedButton("Cancel Reservation", new Color(220, 20, 60));
        refreshButton = themedButton("Refresh", new Color(100, 149, 237));
        backButton = themedButton("Back", new Color(70, 130, 180));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        layeredPane.add(buttonPanel, JLayeredPane.MODAL_LAYER);

        reservationTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{
                "Reservation ID", "Guest ID", "User ID", "Room ID", "Check-In", "Check-Out", "Status"
        }, 0);
        reservationTable.setModel(tableModel);
        reservationTable.setRowHeight(25);
        reservationTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setBounds(420, 60, 500, 500);
        scrollPane.setBorder(new LineBorder(Color.DARK_GRAY, 1));
        layeredPane.add(scrollPane, JLayeredPane.MODAL_LAYER);

        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        addButton.addActionListener(new AddReservationAction());
        updateButton.addActionListener(new UpdateReservationAction());
        cancelButton.addActionListener(new CancelReservationAction());
        refreshButton.addActionListener(new RefreshAction());
        backButton.addActionListener(new BackAction());

        loadReservations();
    }

    private JPanel createLabeledField(String labelText, JComponent field, Font font) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JButton themedButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }

    private void loadReservations() {
        tableModel.setRowCount(0);
        List<Reservation> reservationList = reservationDAO.getAllReservations();
        for (Reservation reservation : reservationList) {
            tableModel.addRow(new Object[]{
                    reservation.getReservationId(),
                    reservation.getGuestId(),
                    reservation.getUserId(),
                    reservation.getRoomId(),
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate(),
                    reservation.getStatus()
            });
        }
    }

    private class AddReservationAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int reservationId = Integer.parseInt(reservationIdField.getText().trim());
                int guestId = Integer.parseInt(guestIdField.getText().trim());
                int userId = userRoleComboBox.getSelectedItem().equals("Admin") ? 1 : 2;
                int roomId = Integer.parseInt(roomIdField.getText().trim());
                LocalDate checkIn = LocalDate.parse(checkInDateField.getText().trim());
                LocalDate checkOut = LocalDate.parse(checkOutDateField.getText().trim());
                String status = statusField.getText().trim();

                Reservation reservation = new Reservation(reservationId, guestId, userId, roomId, checkIn, checkOut, status);
                boolean success = reservationDAO.addReservation(reservation);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Reservation added successfully!");
                    loadReservations();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add reservation!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class UpdateReservationAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int reservationId = Integer.parseInt(reservationIdField.getText().trim());
                int guestId = Integer.parseInt(guestIdField.getText().trim());
                int userId = userRoleComboBox.getSelectedItem().equals("Admin") ? 1 : 2;
                int roomId = Integer.parseInt(roomIdField.getText().trim());
                LocalDate checkIn = LocalDate.parse(checkInDateField.getText().trim());
                LocalDate checkOut = LocalDate.parse(checkOutDateField.getText().trim());
                String status = statusField.getText().trim();

                Reservation reservation = new Reservation(reservationId, guestId, userId, roomId, checkIn, checkOut, status);
                boolean success = reservationDAO.updateReservation(reservation);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Reservation updated successfully!");
                    loadReservations();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update reservation!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class CancelReservationAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int reservationId = Integer.parseInt(reservationIdField.getText().trim());
                boolean success = reservationDAO.cancelReservation(reservationId);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Reservation cancelled successfully!");
                    loadReservations();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to cancel reservation!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class RefreshAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadReservations();
        }
    }

    private class BackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new ReservationManagementForm().setVisible(true);
    }
}
