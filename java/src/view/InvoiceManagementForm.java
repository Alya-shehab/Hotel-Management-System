package view;

import dao.InvoiceDAO;
import model.Invoice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InvoiceManagementForm extends JFrame {

    private JTextField invoiceIdField, reservationIdField, totalAmountField, paymentStatusField, paymentDateField;
    private JButton addButton, updateStatusButton, refreshButton, backButton;
    private JTable invoiceTable;
    private DefaultTableModel tableModel;

    private InvoiceDAO invoiceDAO;

    public InvoiceManagementForm() {
        invoiceDAO = new InvoiceDAO();

        setTitle("Hotel Management System - Manage Invoices");
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
        JLabel titleLabel = new JLabel("Invoice Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(60, 30, 20));
        titleLabel.setBounds(0, 10, 900, 40);
        layeredPane.add(titleLabel, JLayeredPane.PALETTE_LAYER);

        // Input panel layout
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBounds(30, 60, 320, 180);
        inputPanel.setOpaque(false);

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        inputPanel.add(createLabeledField("Invoice ID:", invoiceIdField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Reservation ID:", reservationIdField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Total Amount:", totalAmountField = new JTextField(), labelFont));
        inputPanel.add(createLabeledField("Payment Status:", paymentStatusField = new JTextField("Unpaid"), labelFont));
        inputPanel.add(createLabeledField("Payment Date:", paymentDateField = new JTextField("YYYY-MM-DD"), labelFont));

        layeredPane.add(inputPanel, JLayeredPane.MODAL_LAYER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBounds(30, 260, 320, 100);
        buttonPanel.setOpaque(false);

        addButton = themedButton("Add Invoice", new Color(60, 179, 113));
        updateStatusButton = themedButton("Update Payment", new Color(255, 165, 0));
        refreshButton = themedButton("Refresh", new Color(100, 149, 237));
        backButton = themedButton("Back", new Color(70, 130, 180));

        buttonPanel.add(addButton);
        buttonPanel.add(updateStatusButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        layeredPane.add(buttonPanel, JLayeredPane.MODAL_LAYER);

        // Table Panel
        invoiceTable = new JTable();
        tableModel = new DefaultTableModel(new Object[] {
                "Invoice ID", "Reservation ID", "Total Amount", "Payment Status", "Payment Date"
        }, 0);
        invoiceTable.setModel(tableModel);
        invoiceTable.setRowHeight(25);
        invoiceTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(invoiceTable);
        scrollPane.setBounds(370, 60, 500, 460);
        scrollPane.setBorder(new LineBorder(Color.DARK_GRAY, 1));
        layeredPane.add(scrollPane, JLayeredPane.MODAL_LAYER);

        // Background last
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Button actions (unchanged)
        addButton.addActionListener(new AddInvoiceAction());
        updateStatusButton.addActionListener(new UpdatePaymentAction());
        refreshButton.addActionListener(new RefreshAction());
        backButton.addActionListener(new BackAction());

        loadInvoices();
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

    private JButton themedButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }

    // Logic (same as your original)
    private void loadInvoices() {
        tableModel.setRowCount(0);
        List<Invoice> invoiceList = invoiceDAO.getAllInvoices();
        for (Invoice invoice : invoiceList) {
            tableModel.addRow(new Object[]{
                    invoice.getInvoiceId(),
                    invoice.getReservationId(),
                    invoice.getTotalAmount(),
                    invoice.getPaymentStatus(),
                    invoice.getPaymentDate()
            });
        }
    }

    private class AddInvoiceAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int invoiceId = Integer.parseInt(invoiceIdField.getText().trim());
                int reservationId = Integer.parseInt(reservationIdField.getText().trim());
                BigDecimal totalAmount = new BigDecimal(totalAmountField.getText().trim());
                String paymentStatus = paymentStatusField.getText().trim();
                LocalDate paymentDate = LocalDate.parse(paymentDateField.getText().trim());

                Invoice invoice = new Invoice(invoiceId, reservationId, totalAmount, paymentStatus, paymentDate);
                boolean success = invoiceDAO.addInvoice(invoice);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Invoice added successfully!");
                    loadInvoices();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add invoice!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class UpdatePaymentAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int invoiceId = Integer.parseInt(invoiceIdField.getText().trim());
                String newStatus = paymentStatusField.getText().trim();
                LocalDate paymentDate = LocalDate.parse(paymentDateField.getText().trim());

                boolean success = invoiceDAO.updatePaymentStatus(invoiceId, newStatus, paymentDate);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Payment updated successfully!");
                    loadInvoices();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update payment!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class RefreshAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadInvoices();
        }
    }

    private class BackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new InvoiceManagementForm().setVisible(true);
    }
}
