package za.ac.cput.factory;

import za.ac.cput.domain.Notification;

public class NotificationFactory {

    public static Notification createNotification(String message, String studentID, String eventID) {
        if (message == null || studentID == null || eventID == null) {
            throw new IllegalArgumentException("All fields must be provided and cannot be null.");
        }

        return new Notification.Builder()
                .setMessage(message)
                .setStudentID(studentID)
                .setEventID(eventID)
                .build();
    }
}

