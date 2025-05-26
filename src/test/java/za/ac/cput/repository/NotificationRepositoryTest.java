//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.Notification;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository repository;

    @Test
    void testCreateReadDelete() {
        Notification notification = new Notification.Builder()
                .setNotificationID("N001")
                .setMessage("Event is happening soon!")
                .setStudentID("S123")
                .setEventID("E001")
                .setTimestamp(LocalDateTime.now())
                .build();

        Notification saved = repository.save(notification);
        Optional<Notification> fetched = repository.findById(saved.getNotificationID());

        assertTrue(fetched.isPresent());
        assertEquals("N001", fetched.get().getNotificationID());

        repository.delete(saved);
        assertFalse(repository.existsById("N001"));
    }
}