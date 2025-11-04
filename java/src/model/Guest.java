// File: model/Guest.java

package model;

import java.time.LocalDate;

public class Guest {
    private int guestId;
    private String name;
    private String contactNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    // Constructor with parameters
    public Guest(int guestId, String name, String contactNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        this.guestId = guestId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Default constructor
    public Guest() {}

    // Getters and Setters
    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    // toString method
    @Override
    public String toString() {
        return "Guest{" +
                "guestId=" + guestId +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                '}';
    }
}
