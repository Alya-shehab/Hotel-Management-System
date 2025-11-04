// File: dao/InvoiceDAO.java

package dao;

import database.DBConnection;
import model.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceDAO {

    // Method to add a new invoice
    public boolean addInvoice(Invoice invoice) {
        String sql = "INSERT INTO Invoices (invoice_id, reservation_id, total_amount, payment_status, payment_date) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, invoice.getInvoiceId());
            pstmt.setInt(2, invoice.getReservationId());
            pstmt.setBigDecimal(3, invoice.getTotalAmount());
            pstmt.setString(4, invoice.getPaymentStatus());
            pstmt.setDate(5, java.sql.Date.valueOf(invoice.getPaymentDate()));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error adding invoice: " + e.getMessage());
            return false;
        }
    }

    // Method to update invoice payment status
    public boolean updatePaymentStatus(int invoiceId, String newStatus, LocalDate paymentDate) {
        String sql = "UPDATE Invoices SET payment_status = ?, payment_date = ? WHERE invoice_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus);
            pstmt.setDate(2, java.sql.Date.valueOf(paymentDate));
            pstmt.setInt(3, invoiceId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating payment status: " + e.getMessage());
            return false;
        }
    }

    // Method to fetch an invoice by ID
    public Invoice getInvoiceById(int invoiceId) {
        String sql = "SELECT * FROM Invoices WHERE invoice_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, invoiceId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setInvoiceId(rs.getInt("invoice_id"));
                    invoice.setReservationId(rs.getInt("reservation_id"));
                    invoice.setTotalAmount(rs.getBigDecimal("total_amount"));
                    invoice.setPaymentStatus(rs.getString("payment_status"));
                    invoice.setPaymentDate(rs.getDate("payment_date").toLocalDate());
                    return invoice;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching invoice: " + e.getMessage());
            return null;
        }
    }

    // Method to fetch all invoices
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoiceList = new ArrayList<>();
        String sql = "SELECT * FROM Invoices";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getInt("invoice_id"));
                invoice.setReservationId(rs.getInt("reservation_id"));
                invoice.setTotalAmount(rs.getBigDecimal("total_amount"));
                invoice.setPaymentStatus(rs.getString("payment_status"));
                invoice.setPaymentDate(rs.getDate("payment_date").toLocalDate());
                invoiceList.add(invoice);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching invoices: " + e.getMessage());
        }

        return invoiceList;
    }
}
