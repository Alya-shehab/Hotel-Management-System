// File: model/Service.java

package model;

import java.math.BigDecimal;

public class Service {
    private int serviceId;
    private String name;          // Example: Spa, Gym, Laundry
    private String description;   // Brief description of the service
    private BigDecimal price;     // Cost for the service
    private String availableHours; // Example: "8:00 AM - 10:00 PM"

    // Constructor with parameters
    public Service(int serviceId, String name, String description, BigDecimal price, String availableHours) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableHours = availableHours;
    }

    // Default constructor
    public Service() {}

    // Getters and Setters
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAvailableHours() {
        return availableHours;
    }

    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }

    // toString method
    @Override
    public String toString() {
        return "Service{" +
                "serviceId=" + serviceId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", availableHours='" + availableHours + '\'' +
                '}';
    }
}
