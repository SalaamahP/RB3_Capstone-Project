package za.ac.cput.factory;

import za.ac.cput.domain.Notification;

/**
 * Creates notification instances. Throws IllegalArgumentException for null fields.
 */
public class NotificationFactory {
    public static Notification createNotification(String id, String message, String studentID, String eventID) {
        if (id == null || message == null || studentID == null || eventID == null) {
            throw new IllegalArgumentException("All fields must be provided and cannot be null.");
        }
        return new Notification.Builder()
                .setNotificationID(id)
                .setMessage(message)
                .setStudentID(studentID)
                .setEventID(eventID)
                .build();
    }
}
