// File: view/FeedbackManagementForm.java

package view;

import dao.FeedbackDAO;
import model.Feedback;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class FeedbackManagementForm extends JFrame {

    private JTextField feedbackIdField, guestIdField, categoryField, dateField;
    private JTextArea messageArea;
    private JButton updateButton, deleteButton, refreshButton, backButton;
    private JTable feedbackTable;
    private DefaultTableModel tableModel;

    private FeedbackDAO feedbackDAO;

    public FeedbackManagementForm() {
        feedbackDAO = new FeedbackDAO();

        // Frame settings
        setTitle("Hotel Management System - Manage Feedback");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 248, 220)); // Light cream background

        // Labels and Fields
        JLabel feedbackIdLabel = new JLabel("Feedback ID:");
        feedbackIdLabel.setBounds(30, 30, 100, 25);
        add(feedbackIdLabel);

        feedbackIdField = new JTextField();
        feedbackIdField.setBounds(140, 30, 180, 25);
        add(feedbackIdField);

        JLabel guestIdLabel = new JLabel("Guest ID:");
        guestIdLabel.setBounds(30, 70, 100, 25);
        add(guestIdLabel);

        guestIdField = new JTextField();
        guestIdField.setBounds(140, 70, 180, 25);
        add(guestIdField);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(30, 110, 100, 25);
        add(categoryLabel);

        categoryField = new JTextField();
        categoryField.setBounds(140, 110, 180, 25);
        add(categoryField);

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(30, 150, 100, 25);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(140, 150, 180, 25);
        dateField.setToolTipText("Format: YYYY-MM-DD");
        add(dateField);

        JLabel messageLabel = new JLabel("Message:");
        messageLabel.setBounds(30, 190, 100, 25);
        add(messageLabel);

        messageArea = new JTextArea();
        JScrollPane messageScroll = new JScrollPane(messageArea);
        messageScroll.setBounds(140, 190, 180, 100);
        add(messageScroll);

        // Buttons
        updateButton = new JButton("Update Feedback");
        updateButton.setBounds(30, 310, 140, 30);
        updateButton.setBackground(new Color(255, 165, 0));
        updateButton.setForeground(Color.WHITE);
        add(updateButton);

        deleteButton = new JButton("Delete Feedback");
        deleteButton.setBounds(180, 310, 140, 30);
        deleteButton.setBackground(new Color(220, 20, 60));
        deleteButton.setForeground(Color.WHITE);
        add(deleteButton);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(30, 360, 140, 30);
        refreshButton.setBackground(new Color(100, 149, 237));
        refreshButton.setForeground(Color.WHITE);
        add(refreshButton);

        backButton = new JButton("Back");
        backButton.setBounds(180, 360, 140, 30);
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);
        add(backButton);

        // Table
        feedbackTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"Feedback ID", "Guest ID", "Category", "Date", "Message"}, 0);
        feedbackTable.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        scrollPane.setBounds(350, 30, 560, 500);
        add(scrollPane);

        // Button Actions
        updateButton.addActionListener(new UpdateFeedbackAction());
        deleteButton.addActionListener(new DeleteFeedbackAction());
        refreshButton.addActionListener(new RefreshAction());
        backButton.addActionListener(new BackAction());

        loadFeedback(); // Initial load
    }

    private void loadFeedback() {
        tableModel.setRowCount(0);
        List<Feedback> feedbackList = feedbackDAO.getAllFeedback();
        for (Feedback feedback : feedbackList) {
            tableModel.addRow(new Object[]{
                    feedback.getFeedbackId(),
                    feedback.getGuestId(),
                    feedback.getCategory(),
                    feedback.getDate(),
                    feedback.getMessage()
            });
        }
    }

    private class UpdateFeedbackAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int feedbackId = Integer.parseInt(feedbackIdField.getText().trim());
                int guestId = Integer.parseInt(guestIdField.getText().trim());
                String category = categoryField.getText().trim();
                LocalDate date = LocalDate.parse(dateField.getText().trim());
                String message = messageArea.getText().trim();

                Feedback feedback = new Feedback(feedbackId, guestId, message, category, date);
                boolean success = feedbackDAO.updateFeedback(feedback);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Feedback updated successfully!");
                    loadFeedback();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update feedback!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class DeleteFeedbackAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int feedbackId = Integer.parseInt(feedbackIdField.getText().trim());
                boolean success = feedbackDAO.deleteFeedback(feedbackId);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Feedback deleted successfully!");
                    loadFeedback();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete feedback!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class RefreshAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadFeedback();
        }
    }

    private class BackAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            // Return to AdminDashboard
        }
    }

    public static void main(String[] args) {
        new FeedbackManagementForm().setVisible(true);
    }
}
