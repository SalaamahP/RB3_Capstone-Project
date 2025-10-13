//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Notification;
import za.ac.cput.factory.NotificationFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository repository;

    @Test
    void crudOperations() {
        Notification n = NotificationFactory.createNotification("Test message", "S001", "E001");
        Notification saved = repository.save(n);
        assertNotNull(saved.getNotificationID());

        Notification read = repository.findById(saved.getNotificationID()).orElse(null);
        assertNotNull(read);

        saved = new Notification.Builder()
                .setNotificationID(saved.getNotificationID())
                .setMessage("Updated")
                .setStudentID("S001")
                .setEventID("E001")
                .build();

        repository.save(saved);
        Notification updated = repository.findById(saved.getNotificationID()).orElse(null);
        assertEquals("Updated", updated.getMessage());

        List<Notification> all = repository.findAll();
        assertTrue(all.size() >= 1);

        repository.deleteById(saved.getNotificationID());
        assertFalse(repository.existsById(saved.getNotificationID()));
    }
}

