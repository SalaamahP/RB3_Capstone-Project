package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Notification;
import za.ac.cput.factory.NotificationFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTest {
    private NotificationService service;
    private Notification notification1;
    private Notification notification2;

    @BeforeEach
    void setUp() {
        service = new NotificationServiceImpl();
        notification1 = NotificationFactory.createNotification("N001", "Test Message 1", "S123", "E001");
        notification2 = NotificationFactory.createNotification("N002", "Test Message 2", "S456", "E002");

        service.create(notification1);
        service.create(notification2);
    }

    @Test
    void testCreate() {
        assertNotNull(notification1);
        assertNotNull(notification2);
    }

    @Test
    void testRead() {
        Notification read = service.read(notification1.getNotificationID());
        assertEquals(notification1.getNotificationID(), read.getNotificationID());
    }

    @Test
    void testUpdate() {
        Notification updated = new Notification.Builder()
                .setNotificationID(notification1.getNotificationID())
                .setMessage("Updated Message")
                .setStudentID(notification1.getStudentID())
                .setEventID(notification1.getEventID())
                .setTimestamp(notification1.getTimestamp())
                .build();

        service.update(updated);
        Notification read = service.read(notification1.getNotificationID());
        assertEquals("Updated Message", read.getMessage());
    }

    @Test
    void testDelete() {
        boolean deleted = service.delete(notification2.getNotificationID());
        assertTrue(deleted);
    }

    @Test
    void testGetAll() {
        Set<Notification> all = service.getAll();
        assertEquals(2, all.size());
    }
}
