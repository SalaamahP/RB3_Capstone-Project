/*package za.ac.cput.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Notification;
import za.ac.cput.factory.NotificationFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationServiceTest {

    @Autowired
    private NotificationService service;

    @Test
    void saveAndRetrieve() {
        Notification n = NotificationFactory.createNotification("N300", "Service Test", "S300", "E300");
        service.save(n);
        assertTrue(service.read("N300").isPresent());
        assertEquals("Service Test", service.read("N300").get().getMessage());
    }
}
*/