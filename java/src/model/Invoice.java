// File: model/Invoice.java

package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Invoice {
    private int invoiceId;
    private int reservationId;   // References Reservation
    private BigDecimal totalAmount;
    private String paymentStatus; // Example: Paid, Unpaid, Pending
    private LocalDate paymentDate;

    // Constructor with parameters
    public Invoice(int invoiceId, int reservationId, BigDecimal totalAmount, 
                   String paymentStatus, LocalDate paymentDate) {
        this.invoiceId = invoiceId;
        this.reservationId = reservationId;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    // Default constructor
    public Invoice() {}

    // Getters and Setters
    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    // toString method
    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", reservationId=" + reservationId +
                ", totalAmount=" + totalAmount +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
