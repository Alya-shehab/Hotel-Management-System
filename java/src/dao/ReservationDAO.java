// File: dao/ReservationDAO.java

package dao;

import database.DBConnection;
import model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ReservationDAO {

    // Method to make a new reservation
    public boolean addReservation(Reservation reservation) {
        String sql = "INSERT INTO Reservations (reservation_id, guest_id, user_id, room_id, checkin_date, checkout_date, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservation.getReservationId());
            pstmt.setInt(2, reservation.getGuestId());
            pstmt.setInt(3, reservation.getUserId());
            pstmt.setInt(4, reservation.getRoomId());
            pstmt.setDate(5, java.sql.Date.valueOf(reservation.getCheckInDate()));
            pstmt.setDate(6, java.sql.Date.valueOf(reservation.getCheckOutDate()));
            pstmt.setString(7, reservation.getStatus());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error adding reservation: " + e.getMessage());
            return false;
        }
    }

    // Method to update reservation details
    public boolean updateReservation(Reservation reservation) {
        String sql = "UPDATE Reservations SET guest_id = ?, user_id = ?, room_id = ?, checkin_date = ?, checkout_date = ?, status = ? " +
                     "WHERE reservation_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservation.getGuestId());
            pstmt.setInt(2, reservation.getUserId());
            pstmt.setInt(3, reservation.getRoomId());
            pstmt.setDate(4, java.sql.Date.valueOf(reservation.getCheckInDate()));
            pstmt.setDate(5, java.sql.Date.valueOf(reservation.getCheckOutDate()));
            pstmt.setString(6, reservation.getStatus());
            pstmt.setInt(7, reservation.getReservationId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating reservation: " + e.getMessage());
            return false;
        }
    }

    // Method to cancel a reservation (update status to 'Cancelled')
    public boolean cancelReservation(int reservationId) {
        String sql = "UPDATE Reservations SET status = 'Cancelled' WHERE reservation_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservationId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error cancelling reservation: " + e.getMessage());
            return false;
        }
    }

    // Method to fetch a reservation by ID
    public Reservation getReservationById(int reservationId) {
        String sql = "SELECT * FROM Reservations WHERE reservation_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setReservationId(rs.getInt("reservation_id"));
                    reservation.setGuestId(rs.getInt("guest_id"));
                    reservation.setUserId(rs.getInt("user_id"));
                    reservation.setRoomId(rs.getInt("room_id"));
                    reservation.setCheckInDate(rs.getDate("checkin_date").toLocalDate());
                    reservation.setCheckOutDate(rs.getDate("checkout_date").toLocalDate());
                    reservation.setStatus(rs.getString("status"));
                    return reservation;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching reservation: " + e.getMessage());
            return null;
        }
    }

    // Method to fetch all reservations
    public List<Reservation> getAllReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        String sql = "SELECT * FROM Reservations";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationId(rs.getInt("reservation_id"));
                reservation.setGuestId(rs.getInt("guest_id"));
                reservation.setUserId(rs.getInt("user_id"));
                reservation.setRoomId(rs.getInt("room_id"));
                reservation.setCheckInDate(rs.getDate("checkin_date").toLocalDate());
                reservation.setCheckOutDate(rs.getDate("checkout_date").toLocalDate());
                reservation.setStatus(rs.getString("status"));
                reservationList.add(reservation);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching reservations: " + e.getMessage());
        }

        return reservationList;
    }
}
