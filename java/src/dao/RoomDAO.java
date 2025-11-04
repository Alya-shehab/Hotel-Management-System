// File: dao/RoomDAO.java

package dao;

import database.DBConnection;
import model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class RoomDAO {

    // Method to add a new room
    public boolean addRoom(Room room) {
        String sql = "INSERT INTO Rooms (room_id, type, price, availability, view) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, room.getRoomId());
            pstmt.setString(2, room.getType());
            pstmt.setBigDecimal(3, room.getPrice());
            pstmt.setString(4, room.getAvailability());
            pstmt.setString(5, room.getView());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error adding room: " + e.getMessage());
            return false;
        }
    }

    // Method to update room information
    public boolean updateRoom(Room room) {
        String sql = "UPDATE Rooms SET type = ?, price = ?, availability = ?, view = ? WHERE room_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, room.getType());
            pstmt.setBigDecimal(2, room.getPrice());
            pstmt.setString(3, room.getAvailability());
            pstmt.setString(4, room.getView());
            pstmt.setInt(5, room.getRoomId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
            return false;
        }
    }

    // Method to delete a room by room ID
    public boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM Rooms WHERE room_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting room: " + e.getMessage());
            return false;
        }
    }

    // Method to fetch a single room by ID
    public Room getRoomById(int roomId) {
        String sql = "SELECT * FROM Rooms WHERE room_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Room room = new Room();
                    room.setRoomId(rs.getInt("room_id"));
                    room.setType(rs.getString("type"));
                    room.setPrice(rs.getBigDecimal("price"));
                    room.setAvailability(rs.getString("availability"));
                    room.setView(rs.getString("view"));
                    return room;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching room: " + e.getMessage());
            return null;
        }
    }

    // Method to fetch all available rooms
    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        String sql = "SELECT * FROM Rooms WHERE availability = 'Available'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("room_id"));
                room.setType(rs.getString("type"));
                room.setPrice(rs.getBigDecimal("price"));
                room.setAvailability(rs.getString("availability"));
                room.setView(rs.getString("view"));
                availableRooms.add(room);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching available rooms: " + e.getMessage());
        }

        return availableRooms;
    }

    // Method to fetch all rooms (regardless of availability)
    public List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();
        String sql = "SELECT * FROM Rooms";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("room_id"));
                room.setType(rs.getString("type"));
                room.setPrice(rs.getBigDecimal("price"));
                room.setAvailability(rs.getString("availability"));
                room.setView(rs.getString("view"));
                roomList.add(room);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching all rooms: " + e.getMessage());
        }

        return roomList;
    }
}
