//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
/*package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.Notification;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository repository;

    @Test
    void saveAndFind() {
        Notification n = new Notification.Builder()
                .setNotificationID("N200")
                .setMessage("Repo Test")
                .setStudentID("S200")
                .setEventID("E200")
                .setTimestamp(LocalDateTime.now())
                .build();

        repository.save(n);

        assertTrue(repository.findById("N200").isPresent());
        assertEquals("Repo Test", repository.findById("N200").get().getMessage());
    }
}
*/