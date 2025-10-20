//[author] Jaedon Prince, 230473474
//[date] 20/10/2025

package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String studentID;

    @Column(nullable = false)
    private String eventID;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    protected Notification() {}

    private Notification(Builder builder) {
        this.notificationID = builder.notificationID;
        this.message = builder.message;
        this.studentID = builder.studentID;
        this.eventID = builder.eventID;
        this.timestamp = builder.timestamp != null ? builder.timestamp : LocalDateTime.now();
    }

    // GETTERS AND SETTERS - COMPLETE
    public Long getNotificationID() { return notificationID; }
    public void setNotificationID(Long notificationID) { this.notificationID = notificationID; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStudentID() { return studentID; }
    public void setStudentID(String studentID) { this.studentID = studentID; }

    public String getEventID() { return eventID; }
    public void setEventID(String eventID) { this.eventID = eventID; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationID=" + notificationID +
                ", message='" + message + '\'' +
                ", studentID='" + studentID + '\'' +
                ", eventID='" + eventID + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return Objects.equals(notificationID, that.notificationID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationID);
    }

    public static class Builder {
        private Long notificationID;
        private String message;
        private String studentID;
        private String eventID;
        private LocalDateTime timestamp;

        public Builder setNotificationID(Long notificationID) {
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
            return new Notification(this);
        }
    }
}
