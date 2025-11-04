// File: model/Room.java

package model;

import java.math.BigDecimal;

public class Room {
    private int roomId;
    private String type;          // Example: Single, Double, Suite
    private BigDecimal price;     // Using BigDecimal for money (best practice)
    private String availability;  // Example: Available, Occupied, Under Maintenance
    private String view;          // Example: Sea View, City View, Garden View

    // Constructor with parameters
    public Room(int roomId, String type, BigDecimal price, String availability, String view) {
        this.roomId = roomId;
        this.type = type;
        this.price = price;
        this.availability = availability;
        this.view = view;
    }

    // Default constructor
    public Room() {}

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    // toString method
    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", availability='" + availability + '\'' +
                ", view='" + view + '\'' +
                '}';
    }
}
