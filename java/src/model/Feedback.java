// File: model/Feedback.java

package model;

import java.time.LocalDate;

public class Feedback {
    private int feedbackId;
    private int guestId;      // References Guest who provided the feedback
    private String message;   // Feedback text (complaint, suggestion, review)
    private String category;  // Example: Complaint, Suggestion, Praise
    private LocalDate date;   // When the feedback was submitted

    // Constructor with parameters
    public Feedback(int feedbackId, int guestId, String message, String category, LocalDate date) {
        this.feedbackId = feedbackId;
        this.guestId = guestId;
        this.message = message;
        this.category = category;
        this.date = date;
    }

    // Default constructor
    public Feedback() {}

    // Getters and Setters
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // toString method
    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", guestId=" + guestId +
                ", message='" + message + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                '}';
    }
}
