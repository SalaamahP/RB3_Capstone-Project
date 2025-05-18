package za.ac.cput.factory;

import za.ac.cput.domain.Notification;
import java.time.LocalDateTime;
import java.util.UUID;

//[author] Jaedon Prince, 230473474
//[date] 18/05/2025

public class NotificationFactory {
    /**
     * Creates a new Notification object using the Builder pattern.
     *
     * @param message   The message content of the notification.
     * @param studentID The ID of the student receiving the notification.
     * @param eventID   The ID of the event related to the notification.
     * @return A new Notification object.
     * @throws IllegalArgumentException If any parameter is null or empty.
     */
    public static Notification createNotification(String message, String studentID, String eventID) {
        if (message == null || studentID == null || eventID == null) {
            throw new IllegalArgumentException("All fields must be provided and cannot be null.");
        }

        if (message.trim().isEmpty() || studentID.trim().isEmpty() || eventID.trim().isEmpty()) {
            throw new IllegalArgumentException("All fields must be provided and cannot be empty.");
        }

        String notificationID = UUID.randomUUID().toString(); // Generate a unique ID
        return new Notification.Builder()
                .setNotificationID(notificationID)
                .setMessage(message)
                .setStudentID(studentID)
                .setEventID(eventID)
                .setTimestamp(LocalDateTime.now()) // Set the current timestamp
                .build();
    }
}
