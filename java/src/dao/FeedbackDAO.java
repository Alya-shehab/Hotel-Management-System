// File: dao/FeedbackDAO.java

package dao;

import database.DBConnection;
import model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class FeedbackDAO {

    // Method to add new feedback
    public boolean addFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedback (feedback_id, guest_id, message, category, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, feedback.getFeedbackId());
            pstmt.setInt(2, feedback.getGuestId());
            pstmt.setString(3, feedback.getMessage());
            pstmt.setString(4, feedback.getCategory());
            pstmt.setDate(5, java.sql.Date.valueOf(feedback.getDate()));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error adding feedback: " + e.getMessage());
            return false;
        }
    }

    // Method to update feedback (optional, in case admin wants to edit category or message)
    public boolean updateFeedback(Feedback feedback) {
        String sql = "UPDATE Feedback SET message = ?, category = ?, date = ? WHERE feedback_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, feedback.getMessage());
            pstmt.setString(2, feedback.getCategory());
            pstmt.setDate(3, java.sql.Date.valueOf(feedback.getDate()));
            pstmt.setInt(4, feedback.getFeedbackId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating feedback: " + e.getMessage());
            return false;
        }
    }

    // Method to delete feedback by ID
    public boolean deleteFeedback(int feedbackId) {
        String sql = "DELETE FROM Feedback WHERE feedback_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, feedbackId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting feedback: " + e.getMessage());
            return false;
        }
    }

    // Method to fetch a single feedback by ID
    public Feedback getFeedbackById(int feedbackId) {
        String sql = "SELECT * FROM Feedback WHERE feedback_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, feedbackId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setFeedbackId(rs.getInt("feedback_id"));
                    feedback.setGuestId(rs.getInt("guest_id"));
                    feedback.setMessage(rs.getString("message"));
                    feedback.setCategory(rs.getString("category"));
                    feedback.setDate(rs.getDate("date").toLocalDate());
                    return feedback;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching feedback: " + e.getMessage());
            return null;
        }
    }

    // Method to fetch all feedback entries
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM Feedback";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(rs.getInt("feedback_id"));
                feedback.setGuestId(rs.getInt("guest_id"));
                feedback.setMessage(rs.getString("message"));
                feedback.setCategory(rs.getString("category"));
                feedback.setDate(rs.getDate("date").toLocalDate());
                feedbackList.add(feedback);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching all feedback: " + e.getMessage());
        }

        return feedbackList;
    }
}
