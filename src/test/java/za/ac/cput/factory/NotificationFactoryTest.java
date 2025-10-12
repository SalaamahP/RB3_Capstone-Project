/*package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Notification;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    @Test
    void success_createNotification() {
        Notification n = NotificationFactory.createNotification("N1", "Hello", "S1", "E1");
        assertNotNull(n);
        assertEquals("N1", n.getNotificationID());
        assertEquals("Hello", n.getMessage());
    }

    @Test
    void failure_createNotification_nulls() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> NotificationFactory.createNotification(null, "m", "S1", "E1"));
        assertEquals("All fields must be provided and cannot be null.", ex.getMessage());
    }
}
*/