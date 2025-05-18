package za.ac.cput.Factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Notification;

import static org.junit.jupiter.api.Assertions.*;

//[author] Jaedon Prince, 230473474
//[date] 17/03/2025

class NotificationFactoryTest {

    /**
     * Test for successfully creating a Notification.
     */
    @Test
    void testCreateNotification_Success() {
        Notification notification = NotificationFactory.createNotification(
                "Event Reminder", "S123", "E789");

        assertNotNull(notification, "Notification should not be null");
        assertNotNull(notification.getNotificationID(), "Notification ID should not be null");
        assertEquals("Event Reminder", notification.getMessage(), "Message should match");
        assertEquals("S123", notification.getStudentID(), "Student ID should match");
        assertEquals("E789", notification.getEventID(), "Event ID should match");
        assertNotNull(notification.getTimestamp(), "Timestamp should be set");
    }

    /**
     * Test for failure when null values are provided.
     */
    @Test
    void testCreateNotification_Failure_NullValues() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> NotificationFactory.createNotification(null, "S123", "E789")
        );

        assertEquals("All fields must be provided and cannot be null.", exception.getMessage());
    }

    /**
     * Test for failure when empty string values are provided.
     */
    @Test
    void testCreateNotification_Failure_EmptyValues() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> NotificationFactory.createNotification("", "S123", "E789")
        );

        assertEquals("All fields must be provided and cannot be empty.", exception.getMessage());
    }
}

