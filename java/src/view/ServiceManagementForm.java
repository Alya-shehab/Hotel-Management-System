package view;

import dao.ServiceDAO;
import model.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;

public class ServiceManagementForm extends JFrame {

    private JTextField serviceIdField, nameField, descriptionField, priceField, availableHoursField;
    private JButton addButton, updateButton, deleteButton, refreshButton, backButton;
    private JTable serviceTable;
    private DefaultTableModel tableModel;

    private ServiceDAO serviceDAO;

    public ServiceManagementForm() {
        serviceDAO = new ServiceDAO();

        setTitle("Hotel Management System - Manage Services");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Optional background image
        JLabel background = new JLabel(new ImageIcon(
                new ImageIcon("resources/images/admin_db.png").getImage().getScaledInstance(950, 600, Image.SCALE_SMOOTH)));
        background.setBounds(0, 0, 950, 600);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 950, 600);
        add(layeredPane);

        // Title
        JLabel titleLabel = new JLabel("Service Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(60, 30, 20));
        titleLabel.setBounds(0, 10, 950, 40);
        layeredPane.add(titleLabel, JLayeredPane.PALETTE_LAYER);

        // Input Fields Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBounds(30, 60, 360, 180);
        inputPanel.setOpaque(false);

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        inputPanel.add(createLabeledField("Service ID:", serviceIdField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Name:", nameField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Description:", descriptionField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Price:", priceField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Available Hours:", availableHoursField = new JTextField(), labelFont));

        layeredPane.add(inputPanel, JLayeredPane.MODAL_LAYER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBounds(30, 260, 360, 100);
        buttonPanel.setOpaque(false);

        addButton = themedButton("Add Service", new Color(60, 179, 113));
        updateButton = themedButton("Update Service", new Color(255, 165, 0));
        deleteButton = themedButton("Delete Service", new Color(220, 20, 60));
        refreshButton = themedButton("Refresh", new Color(100, 149, 237));
        backButton = themedButton("Back", new Color(70, 130, 180));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        layeredPane.add(buttonPanel, JLayeredPane.MODAL_LAYER);

        // Table
        serviceTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{
                "Service ID", "Name", "Description", "Price", "Available Hours"
        }, 0);
        serviceTable.setModel(tableModel);
        serviceTable.setRowHeight(25);
        serviceTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(serviceTable);
        scrollPane.setBounds(410, 60, 510, 470);
        scrollPane.setBorder(new LineBorder(Color.DARK_GRAY, 1));
        layeredPane.add(scrollPane, JLayeredPane.MODAL_LAYER);

        // Add background last
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Button Actions
        addButton.addActionListener(new AddServiceAction());
        updateButton.addActionListener(new UpdateServiceAction());
        deleteButton.addActionListener(new DeleteServiceAction());
        refreshButton.addActionListener(new RefreshAction());
        backButton.addActionListener(new BackAction());

        loadServices();
    }

    // UI helpers
    private JPanel createLabeledField(String labelText, JTextField field, Font font) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JButton themedButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }

    // Logic stays the same
    private void loadServices() {
        tableModel.setRowCount(0);
        List<Service> serviceList = serviceDAO.getAllServices();
        for (Service service : serviceList) {
            tableModel.addRow(new Object[]{
                    service.getServiceId(),
                    service.getName(),
                    service.getDescription(),
                    service.getPrice(),
                    service.getAvailableHours()
            });
        }
    }

    private class AddServiceAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int serviceId = Integer.parseInt(serviceIdField.getText().trim());
                String name = nameField.getText().trim();
                String description = descriptionField.getText().trim();
                BigDecimal price = new BigDecimal(priceField.getText().trim());
                String availableHours = availableHoursField.getText().trim();

                Service service = new Service(serviceId, name, description, price, availableHours);
                boolean success = serviceDAO.addService(service);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Service added successfully!");
                    loadServices();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add service!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class UpdateServiceAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int serviceId = Integer.parseInt(serviceIdField.getText().trim());
                String name = nameField.getText().trim();
                String description = descriptionField.getText().trim();
                BigDecimal price = new BigDecimal(priceField.getText().trim());
                String availableHours = availableHoursField.getText().trim();

                Service service = new Service(serviceId, name, description, price, availableHours);
                boolean success = serviceDAO.updateService(service);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Service updated successfully!");
                    loadServices();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update service!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class DeleteServiceAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int serviceId = Integer.parseInt(serviceIdField.getText().trim());
                boolean success = serviceDAO.deleteService(serviceId);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Service deleted successfully!");
                    loadServices();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete service!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class RefreshAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadServices();
        }
    }

    private class BackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new ServiceManagementForm().setVisible(true);
    }
}
