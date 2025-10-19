//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Notification;
import za.ac.cput.factory.NotificationFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotificationControllerTest {

    private static Notification notification;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String BASE_URL;

    @BeforeEach
    void init() {
        BASE_URL = "http://localhost:" + port + "/SEMS/notifications";

    }

    @BeforeAll
    public static void setUp() {
        notification = NotificationFactory.createNotification(
                "Your event is starting in 1 hour",
                "STU001",
                "EVT001"
        );
    }

    @Test
    @Order(1)
    void testCreate() {
        ResponseEntity<Notification> postResponse = restTemplate.postForEntity(BASE_URL, notification, Notification.class);
        assertNotNull(postResponse.getBody());
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        notification = postResponse.getBody();
        assertNotNull(notification.getNotificationID());
        assertEquals("Your event is starting in 1 hour", notification.getMessage());
        assertEquals("STU001", notification.getStudentID());
        assertEquals("EVT001", notification.getEventID());
        System.out.println("Created: " + notification);
    }

    @Test
    @Order(2)
    void testRead() {
        ResponseEntity<Notification> response = restTemplate.getForEntity(BASE_URL + "/" + notification.getNotificationID(), Notification.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(notification.getNotificationID(), response.getBody().getNotificationID());
        assertEquals("Your event is starting in 1 hour", response.getBody().getMessage());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void testUpdate() {
        Notification updatedNotification = new Notification.Builder()
                .setNotificationID(notification.getNotificationID())
                .setMessage("Updated: Event is starting now!")
                .setStudentID(notification.getStudentID())
                .setEventID(notification.getEventID())
                .setTimestamp(notification.getTimestamp())
                .build();

        restTemplate.put(BASE_URL, updatedNotification);

        ResponseEntity<Notification> response = restTemplate.getForEntity(BASE_URL + "/" + notification.getNotificationID(), Notification.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated: Event is starting now!", response.getBody().getMessage());

        notification = response.getBody();
        System.out.println("Updated: " + notification);
    }

    @Test
    @Order(4)
    void testGetAll() {
        ResponseEntity<Notification[]> response = restTemplate.getForEntity(BASE_URL, Notification[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Notifications:");
        for (Notification n : response.getBody()) {
            System.out.println(n);
        }
    }

    @Test
    @Order(5)
    void testDelete() {
        restTemplate.delete(BASE_URL + "/" + notification.getNotificationID());

        ResponseEntity<Notification> response = restTemplate.getForEntity(BASE_URL + "/" + notification.getNotificationID(), Notification.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted Notification ID: " + notification.getNotificationID());
    }

    @Test
    @Order(6)
    void testReadNonExistent() {
        ResponseEntity<Notification> response = restTemplate.getForEntity(BASE_URL + "/99999", Notification.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Read non-existent returned 404 as expected");
    }

    @Test
    @Order(7)
    void testUpdateNonExistent() {
        Notification nonExistent = new Notification.Builder()
                .setNotificationID(99999L)
                .setMessage("This should not update")
                .setStudentID("STU999")
                .setEventID("EVT999")
                .build();

        restTemplate.put(BASE_URL, nonExistent);

        ResponseEntity<Notification> response = restTemplate.getForEntity(BASE_URL + "/99999", Notification.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Update non-existent returned 404 as expected");
    }
}
