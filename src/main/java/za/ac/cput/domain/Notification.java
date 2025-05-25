package za.ac.cput.domain;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
//[author] Jaedon Prince, 230473474
//[date] 11/05/2025
/**
 * Represents a notification that is sent to a student about an event.
 * Implements the Builder Pattern for easy and flexible object creation.
 */
public class Notification {
    private final String notificationID; // Unique identifier for the notification
    private final String message; // Content of the notification
    private final String studentID; // ID of the student receiving the notification
    private final String eventID; // ID of the event linked to the notification
    private final LocalDateTime timestamp; // Time the notification was created

    private Notification(Builder builder) {
        this.notificationID = builder.notificationID;
        this.message = builder.message;
        this.studentID = builder.studentID;
        this.eventID = builder.eventID;
        this.timestamp = builder.timestamp;
    }

    // Getters only since the class is immutable
    public String getNotificationID() { return notificationID; }
    public String getMessage() { return message; }
    public String getStudentID() { return studentID; }
    public String getEventID() { return eventID; }
    public LocalDateTime getTimestamp() { return timestamp; }

    /**
     * Sends a notification to a specific student.
     */
    public void sendToStudent() {
        System.out.println("Notification sent to student (ID: " + studentID + "): " + message);
    }

    /**
     * Sends a notification to all attendees of a specific event.
     */
    public void sendToAll() {
        System.out.println("Notification sent to all attendees of event (ID: " + eventID + "): " + message);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationID='" + notificationID + '\'' +
                ", message='" + message + '\'' +
                ", studentID='" + studentID + '\'' +
                ", eventID='" + eventID + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    /**
     * Builder class for Notification
     */
    public static class Builder {
        private String notificationID;
        private String message;
        private String studentID;
        private String eventID;
        private LocalDateTime timestamp;

        public Builder setNotificationID(String notificationID) {
            this.notificationID = notificationID;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setStudentID(String studentID) {
            this.studentID = studentID;
            return this;
        }

        public Builder setEventID(String eventID) {
            this.eventID = eventID;
            return this;
        }

        public Builder setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Notification build() {
            if (this.timestamp == null) {
                this.timestamp = LocalDateTime.now(); // Auto-set timestamp if not provided
            }
            return new Notification(this);
        }
    }
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
