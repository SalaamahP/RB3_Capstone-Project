
//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Notification;
import za.ac.cput.factory.NotificationFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository repository;

    @Test
    @Order(1)
    void testSave() {
        Notification notification = NotificationFactory.createNotification(
                "Test notification for save",
                "STU001",
                "EVT001"
        );

        Notification saved = repository.save(notification);
        assertNotNull(saved);
        assertNotNull(saved.getNotificationID());
        assertEquals("Test notification for save", saved.getMessage());
        assertEquals("STU001", saved.getStudentID());
        assertEquals("EVT001", saved.getEventID());
        System.out.println("Saved: " + saved);
    }

    @Test
    @Order(2)
    void testFindById() {
        Notification notification = NotificationFactory.createNotification(
                "Test notification for find",
                "STU002",
                "EVT002"
        );

        Notification saved = repository.save(notification);
        Optional<Notification> found = repository.findById(saved.getNotificationID());

        assertTrue(found.isPresent());
        assertEquals(saved.getNotificationID(), found.get().getNotificationID());
        assertEquals("Test notification for find", found.get().getMessage());
        System.out.println("Found: " + found.get());
    }

    @Test
    @Order(3)
    void testFindAll() {
        // Clear any existing data first
        repository.deleteAll();

        Notification notification1 = NotificationFactory.createNotification(
                "Test notification 1",
                "STU003",
                "EVT003"
        );

        Notification notification2 = NotificationFactory.createNotification(
                "Test notification 2",
                "STU004",
                "EVT004"
        );

        repository.save(notification1);
        repository.save(notification2);

        List<Notification> notifications = repository.findAll();
        assertNotNull(notifications);
        assertTrue(notifications.size() >= 2);
        System.out.println("All Notifications:");
        notifications.forEach(System.out::println);
    }

    @Test
    @Order(4)
    void testUpdate() {
        Notification notification = NotificationFactory.createNotification(
                "Original message",
                "STU005",
                "EVT005"
        );

        Notification saved = repository.save(notification);

        // Create a new instance with updated data
        Notification updated = new Notification.Builder()
                .setNotificationID(saved.getNotificationID())
                .setMessage("Updated message")
                .setStudentID(saved.getStudentID())
                .setEventID(saved.getEventID())
                .setTimestamp(saved.getTimestamp())
                .build();

        Notification result = repository.save(updated);
        assertNotNull(result);
        assertEquals("Updated message", result.getMessage());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(5)
    void testDelete() {
        Notification notification = NotificationFactory.createNotification(
                "To be deleted",
                "STU006",
                "EVT006"
        );

        Notification saved = repository.save(notification);
        Long id = saved.getNotificationID();

        repository.deleteById(id);

        Optional<Notification> found = repository.findById(id);
        assertFalse(found.isPresent());
        System.out.println("Deleted notification with ID: " + id);
    }

    @Test
    @Order(6)
    void testExistsById() {
        Notification notification = NotificationFactory.createNotification(
                "Exists test",
                "STU007",
                "EVT007"
        );

        Notification saved = repository.save(notification);
        assertTrue(repository.existsById(saved.getNotificationID()));

        repository.deleteById(saved.getNotificationID());
        assertFalse(repository.existsById(saved.getNotificationID()));
    }
}
