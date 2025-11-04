// File: model/Reservation.java

package model;

import java.time.LocalDate;

public class Reservation {
    private int reservationId;
    private int guestId;   // References Guest
    private int userId;    // References User (who handled the booking)
    private int roomId;    // References Room
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status; // Example: Confirmed, Canceled, Pending

    // Constructor with parameters
    public Reservation(int reservationId, int guestId, int userId, int roomId, 
                       LocalDate checkInDate, LocalDate checkOutDate, String status) {
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }

    // Default constructor
    public Reservation() {}

    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method
    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", guestId=" + guestId +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", status='" + status + '\'' +
                '}';
    }
}
