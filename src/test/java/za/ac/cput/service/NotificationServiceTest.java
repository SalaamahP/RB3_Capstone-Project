//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Notification;
import za.ac.cput.factory.NotificationFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotificationServiceTest {

    @Autowired
    private NotificationService service;

    private static Notification notification1;
    private static Notification notification2;

    @BeforeAll
    static void setUp() {
        notification1 = NotificationFactory.createNotification(
                "Your event is starting in 1 hour",
                "STU001",
                "EVT001"
        );

        notification2 = NotificationFactory.createNotification(
                "Event has been cancelled",
                "STU002",
                "EVT002"
        );
    }

    @Test
    @Order(1)
    void testCreate() {
        Notification created = service.create(notification1);
        assertNotNull(created);
        assertNotNull(created.getNotificationID());
        assertEquals("Your event is starting in 1 hour", created.getMessage());
        assertEquals("STU001", created.getStudentID());
        assertEquals("EVT001", created.getEventID());
        notification1 = created;
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void testRead() {
        Notification created = service.create(notification1);
        Notification read = service.read(created.getNotificationID());
        assertNotNull(read);
        assertEquals(created.getNotificationID(), read.getNotificationID());
        assertEquals("Your event is starting in 1 hour", read.getMessage());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void testUpdate() {
        Notification created = service.create(notification1);

        Notification updated = new Notification.Builder()
                .setNotificationID(created.getNotificationID())
                .setMessage("Updated: Event is starting now!")
                .setStudentID(created.getStudentID())
                .setEventID(created.getEventID())
                .setTimestamp(created.getTimestamp())
                .build();

        Notification result = service.update(updated);
        assertNotNull(result);
        assertEquals("Updated: Event is starting now!", result.getMessage());
        assertEquals(created.getNotificationID(), result.getNotificationID());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void testUpdateNonExistent() {
        Notification nonExistent = new Notification.Builder()
                .setNotificationID(99999L)
                .setMessage("This should not update")
                .setStudentID("STU999")
                .setEventID("EVT999")
                .build();

        Notification result = service.update(nonExistent);
        assertNull(result);
        System.out.println("Update non-existent returned null as expected");
    }

    @Test
    @Order(5)
    void testGetAll() {
        service.create(notification1);
        service.create(notification2);

        List<Notification> notifications = service.getAll();
        assertNotNull(notifications);
        assertTrue(notifications.size() >= 2);
        System.out.println("All Notifications:");
        notifications.forEach(System.out::println);
    }

    @Test
    @Order(6)
    void testDelete() {
        Notification created = service.create(notification1);
        Long id = created.getNotificationID();

        boolean deleted = service.delete(id);
        assertTrue(deleted);

        Notification read = service.read(id);
        assertNull(read);
        System.out.println("Deleted notification with ID: " + id);
    }

    @Test
    @Order(7)
    void testDeleteNonExistent() {
        boolean deleted = service.delete(99999L);
        assertFalse(deleted);
        System.out.println("Delete non-existent returned false as expected");
    }

    @Test
    @Order(8)
    void testReadNonExistent() {
        Notification read = service.read(99999L);
        assertNull(read);
        System.out.println("Read non-existent returned null as expected");
    }
}