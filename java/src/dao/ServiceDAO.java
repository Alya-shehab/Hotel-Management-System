// File: dao/ServiceDAO.java

package dao;

import database.DBConnection;
import model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class ServiceDAO {

    // Method to add a new service
    public boolean addService(Service service) {
        String sql = "INSERT INTO Services (service_id, name, description, price, available_hours) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, service.getServiceId());
            pstmt.setString(2, service.getName());
            pstmt.setString(3, service.getDescription());
            pstmt.setBigDecimal(4, service.getPrice());
            pstmt.setString(5, service.getAvailableHours());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error adding service: " + e.getMessage());
            return false;
        }
    }

    // Method to update service details
    public boolean updateService(Service service) {
        String sql = "UPDATE Services SET name = ?, description = ?, price = ?, available_hours = ? WHERE service_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, service.getName());
            pstmt.setString(2, service.getDescription());
            pstmt.setBigDecimal(3, service.getPrice());
            pstmt.setString(4, service.getAvailableHours());
            pstmt.setInt(5, service.getServiceId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating service: " + e.getMessage());
            return false;
        }
    }

    // Method to delete a service by ID
    public boolean deleteService(int serviceId) {
        String sql = "DELETE FROM Services WHERE service_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, serviceId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting service: " + e.getMessage());
            return false;
        }
    }

    // Method to fetch a single service by ID
    public Service getServiceById(int serviceId) {
        String sql = "SELECT * FROM Services WHERE service_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, serviceId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Service service = new Service();
                    service.setServiceId(rs.getInt("service_id"));
                    service.setName(rs.getString("name"));
                    service.setDescription(rs.getString("description"));
                    service.setPrice(rs.getBigDecimal("price"));
                    service.setAvailableHours(rs.getString("available_hours"));
                    return service;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching service: " + e.getMessage());
            return null;
        }
    }

    // Method to fetch all services
    public List<Service> getAllServices() {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT * FROM Services";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Service service = new Service();
                service.setServiceId(rs.getInt("service_id"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setPrice(rs.getBigDecimal("price"));
                service.setAvailableHours(rs.getString("available_hours"));
                serviceList.add(service);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching services: " + e.getMessage());
        }

        return serviceList;
    }
}
