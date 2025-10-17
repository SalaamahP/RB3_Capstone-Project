//[author] Jaedon Prince, 230473474
//[date] 18/05/2025
package za.ac.cput.factory;

import za.ac.cput.domain.Notification;
import java.time.LocalDateTime;

public class NotificationFactory {

    public static Notification createNotification(String message, String studentID, String eventID) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if (studentID == null || studentID.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (eventID == null || eventID.trim().isEmpty()) {
            throw new IllegalArgumentException("Event ID cannot be null or empty");
        }

        return new Notification.Builder()
                .setMessage(message)
                .setStudentID(studentID)
                .setEventID(eventID)
                .setTimestamp(LocalDateTime.now())
                .build();
    }

    public static Notification createNotificationWithTimestamp(String message, String studentID, String eventID, LocalDateTime timestamp) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if (studentID == null || studentID.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (eventID == null || eventID.trim().isEmpty()) {
            throw new IllegalArgumentException("Event ID cannot be null or empty");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        return new Notification.Builder()
                .setMessage(message)
                .setStudentID(studentID)
                .setEventID(eventID)
                .setTimestamp(timestamp)
                .build();
    }
}

