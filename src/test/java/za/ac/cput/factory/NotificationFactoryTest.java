//[author] Jaedon Prince, 230473474
//[date] 18/05/2025
package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Notification;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    @Test
    void testCreateNotificationSuccess() {
        Notification notification = NotificationFactory.createNotification(
                "Your event is starting soon!",
                "STU001",
                "EVT001"
        );

        assertNotNull(notification);
        assertEquals("Your event is starting soon!", notification.getMessage());
        assertEquals("STU001", notification.getStudentID());
        assertEquals("EVT001", notification.getEventID());
        assertNotNull(notification.getTimestamp());
        System.out.println("Created Notification: " + notification);
    }

    @Test
    void testCreateNotificationWithTimestamp() {
        LocalDateTime timestamp = LocalDateTime.of(2025, 5, 18, 10, 30);
        Notification notification = NotificationFactory.createNotificationWithTimestamp(
                "Event reminder",
                "STU002",
                "EVT002",
                timestamp
        );

        assertNotNull(notification);
        assertEquals("Event reminder", notification.getMessage());
        assertEquals("STU002", notification.getStudentID());
        assertEquals("EVT002", notification.getEventID());
        assertEquals(timestamp, notification.getTimestamp());
        System.out.println("Created Notification with Timestamp: " + notification);
    }

    @Test
    void testCreateNotificationWithNullMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                NotificationFactory.createNotification(null, "STU001", "EVT001")
        );
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateNotificationWithEmptyMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                NotificationFactory.createNotification("   ", "STU001", "EVT001")
        );
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateNotificationWithNullStudentID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                NotificationFactory.createNotification("Test message", null, "EVT001")
        );
        assertEquals("Student ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateNotificationWithEmptyStudentID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                NotificationFactory.createNotification("Test message", "", "EVT001")
        );
        assertEquals("Student ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateNotificationWithNullEventID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                NotificationFactory.createNotification("Test message", "STU001", null)
        );
        assertEquals("Event ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateNotificationWithEmptyEventID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                NotificationFactory.createNotification("Test message", "STU001", "")
        );
        assertEquals("Event ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateNotificationWithTimestampNullTimestamp() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                NotificationFactory.createNotificationWithTimestamp("Test message", "STU001", "EVT001", null)
        );
        assertEquals("Timestamp cannot be null", exception.getMessage());
    }
}
