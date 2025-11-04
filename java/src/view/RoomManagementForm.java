package view;

import dao.RoomDAO;
import model.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;

public class RoomManagementForm extends JFrame {

    private JTextField roomIdField, typeField, priceField, availabilityField, viewField;
    private JButton addButton, updateButton, deleteButton, refreshButton, backButton;
    private JTable roomTable;
    private DefaultTableModel tableModel;

    private RoomDAO roomDAO;

    public RoomManagementForm() {
        roomDAO = new RoomDAO();

        setTitle("Hotel Management System - Manage Rooms");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background Image
        JLabel background = new JLabel(new ImageIcon(new ImageIcon("resources/images/admin_db.png")
                .getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH)));
        background.setBounds(0, 0, 900, 600);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 900, 600);
        add(layeredPane);

        // Title Panel
        JLabel titleLabel = new JLabel("Room Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(60, 30, 20));
        titleLabel.setBounds(0, 10, 900, 40);
        layeredPane.add(titleLabel, JLayeredPane.PALETTE_LAYER);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBounds(30, 60, 300, 180);
        inputPanel.setOpaque(false);

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        inputPanel.add(new JLabel("Room ID:", JLabel.RIGHT)).setFont(labelFont);
        roomIdField = new JTextField();
        inputPanel.add(roomIdField);

        inputPanel.add(new JLabel("Type:", JLabel.RIGHT)).setFont(labelFont);
        typeField = new JTextField();
        inputPanel.add(typeField);

        inputPanel.add(new JLabel("Price:", JLabel.RIGHT)).setFont(labelFont);
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Availability:", JLabel.RIGHT)).setFont(labelFont);
        availabilityField = new JTextField();
        inputPanel.add(availabilityField);

        inputPanel.add(new JLabel("View:", JLabel.RIGHT)).setFont(labelFont);
        viewField = new JTextField();
        inputPanel.add(viewField);

        layeredPane.add(inputPanel, JLayeredPane.MODAL_LAYER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBounds(30, 260, 300, 100);
        buttonPanel.setOpaque(false);

        addButton = themedButton("Add", new Color(60, 179, 113));
        updateButton = themedButton("Update", new Color(255, 165, 0));
        deleteButton = themedButton("Delete", new Color(220, 20, 60));
        refreshButton = themedButton("Refresh", new Color(70, 130, 180));
        backButton = themedButton("Back", new Color(128, 0, 128));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        layeredPane.add(buttonPanel, JLayeredPane.MODAL_LAYER);

        // Table Setup
        roomTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"Room ID", "Type", "Price", "Availability", "View"}, 0);
        roomTable.setModel(tableModel);
        roomTable.setFont(new Font("Arial", Font.PLAIN, 14));
        roomTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(roomTable);
        scrollPane.setBounds(350, 60, 500, 480);
        scrollPane.setBorder(new LineBorder(Color.DARK_GRAY, 1));
        layeredPane.add(scrollPane, JLayeredPane.MODAL_LAYER);

        // Add background last
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Button Actions
        addButton.addActionListener(new AddRoomAction());
        updateButton.addActionListener(new UpdateRoomAction());
        deleteButton.addActionListener(new DeleteRoomAction());
        refreshButton.addActionListener(new RefreshAction());
        backButton.addActionListener(new BackAction());

        loadRooms();
    }

    private JButton themedButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(new RoundBorder(10));
        return button;
    }

    private void loadRooms() {
        tableModel.setRowCount(0);
        List<Room> roomList = roomDAO.getAllRooms();
        for (Room room : roomList) {
            tableModel.addRow(new Object[]{
                    room.getRoomId(),
                    room.getType(),
                    room.getPrice(),
                    room.getAvailability(),
                    room.getView()
            });
        }
    }

    // Round border class (inline alternative to util.RoundedBorder)
    class RoundBorder extends LineBorder {
        public RoundBorder(int radius) {
            super(Color.DARK_GRAY, 1, true);
        }
    }

    // Action classes (same logic as before)
    private class AddRoomAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int roomId = Integer.parseInt(roomIdField.getText().trim());
                String type = typeField.getText().trim();
                BigDecimal price = new BigDecimal(priceField.getText().trim());
                String availability = availabilityField.getText().trim();
                String view = viewField.getText().trim();

                Room room = new Room(roomId, type, price, availability, view);
                if (roomDAO.addRoom(room)) {
                    JOptionPane.showMessageDialog(null, "Room added successfully!");
                    loadRooms();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add room!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class UpdateRoomAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int roomId = Integer.parseInt(roomIdField.getText().trim());
                String type = typeField.getText().trim();
                BigDecimal price = new BigDecimal(priceField.getText().trim());
                String availability = availabilityField.getText().trim();
                String view = viewField.getText().trim();

                Room room = new Room(roomId, type, price, availability, view);
                if (roomDAO.updateRoom(room)) {
                    JOptionPane.showMessageDialog(null, "Room updated successfully!");
                    loadRooms();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update room!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class DeleteRoomAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int roomId = Integer.parseInt(roomIdField.getText().trim());
                if (roomDAO.deleteRoom(roomId)) {
                    JOptionPane.showMessageDialog(null, "Room deleted successfully!");
                    loadRooms();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete room!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class RefreshAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadRooms();
        }
    }

    private class BackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            // new AdminDashboard(adminUser).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new RoomManagementForm().setVisible(true);
    }
}
