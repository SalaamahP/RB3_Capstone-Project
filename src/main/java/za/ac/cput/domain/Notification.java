//[author] Jaedon Prince, 230473474
//[date] 11/05/2025
/*package za.ac.cput.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Notification entity (builder-based).
 * Table should be created by your project's SQL (schema.sql) per team lead.
 */
/*@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @Column(name = "notification_id", length = 50, nullable = false)
    private String notificationID;

    @Column(name = "message", length = 500, nullable = false)
    private String message;

    @Column(name = "student_id", length = 50, nullable = false)
    private String studentID;

    @Column(name = "event_id", length = 50, nullable = false)
    private String eventID;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    protected Notification() {
        // for JPA
    }

    private Notification(Builder b) {
        this.notificationID = b.notificationID;
        this.message = b.message;
        this.studentID = b.studentID;
        this.eventID = b.eventID;
        this.timestamp = (b.timestamp == null) ? LocalDateTime.now() : b.timestamp;
    }

    public String getNotificationID() { return notificationID; }
    public String getMessage() { return message; }
    public String getStudentID() { return studentID; }
    public String getEventID() { return eventID; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "notification{" +
                "notificationID='" + notificationID + '\'' +
                ", message='" + message + '\'' +
                ", studentID='" + studentID + '\'' +
                ", eventID='" + eventID + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public static class Builder {
        private String notificationID;
        private String message;
        private String studentID;
        private String eventID;
        private LocalDateTime timestamp;

        public Builder setNotificationID(String notificationID) { this.notificationID = notificationID; return this; }
        public Builder setMessage(String message) { this.message = message; return this; }
        public Builder setStudentID(String studentID) { this.studentID = studentID; return this; }
        public Builder setEventID(String eventID) { this.eventID = eventID; return this; }
        public Builder setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public Notification build() { return new Notification(this); }
    }
}
*/