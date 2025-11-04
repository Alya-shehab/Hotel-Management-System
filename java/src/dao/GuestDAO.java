// File: dao/GuestDAO.java

package dao;

import database.DBConnection;
import model.Guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class GuestDAO {

    // Method to insert a new guest
    public boolean addGuest(Guest guest) {
        String sql = "INSERT INTO Guests (guest_id, name, contact_number, checkin_date, checkout_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, guest.getGuestId());
            pstmt.setString(2, guest.getName());
            pstmt.setString(3, guest.getContactNumber());
            pstmt.setDate(4, java.sql.Date.valueOf(guest.getCheckInDate()));
            pstmt.setDate(5, java.sql.Date.valueOf(guest.getCheckOutDate()));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error adding guest: " + e.getMessage());
            return false;
        }
    }

    // Method to update guest information
    public boolean updateGuest(Guest guest) {
        String sql = "UPDATE Guests SET name = ?, contact_number = ?, checkin_date = ?, checkout_date = ? WHERE guest_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, guest.getName());
            pstmt.setString(2, guest.getContactNumber());
            pstmt.setDate(3, java.sql.Date.valueOf(guest.getCheckInDate()));
            pstmt.setDate(4, java.sql.Date.valueOf(guest.getCheckOutDate()));
            pstmt.setInt(5, guest.getGuestId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating guest: " + e.getMessage());
            return false;
        }
    }

    // Method to delete a guest by ID
    public boolean deleteGuest(int guestId) {
        String sql = "DELETE FROM Guests WHERE guest_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, guestId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting guest: " + e.getMessage());
            return false;
        }
    }

    // Method to fetch a single guest by ID
    public Guest getGuestById(int guestId) {
        String sql = "SELECT * FROM Guests WHERE guest_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, guestId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Guest guest = new Guest();
                    guest.setGuestId(rs.getInt("guest_id"));
                    guest.setName(rs.getString("name"));
                    guest.setContactNumber(rs.getString("contact_number"));
                    guest.setCheckInDate(rs.getDate("checkin_date").toLocalDate());
                    guest.setCheckOutDate(rs.getDate("checkout_date").toLocalDate());
                    return guest;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching guest: " + e.getMessage());
            return null;
        }
    }

    // Method to fetch all guests
    public List<Guest> getAllGuests() {
        List<Guest> guestList = new ArrayList<>();
        String sql = "SELECT * FROM Guests";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Guest guest = new Guest();
                guest.setGuestId(rs.getInt("guest_id"));
                guest.setName(rs.getString("name"));
                guest.setContactNumber(rs.getString("contact_number"));
                guest.setCheckInDate(rs.getDate("checkin_date").toLocalDate());
                guest.setCheckOutDate(rs.getDate("checkout_date").toLocalDate());
                guestList.add(guest);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching guests: " + e.getMessage());
        }

        return guestList;
    }
}
