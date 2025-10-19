package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Event;
import za.ac.cput.domain.Event.EventCategory;
import za.ac.cput.domain.Venue;
import za.ac.cput.factory.EventFactory;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventControllerTest {

    private static Event event;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String BASE_URL;

    @BeforeEach
    void init() {
        BASE_URL = "http://localhost:" + port + "/api/event";
    }

    @BeforeAll
    public static void setUp() {
        Venue venue = null;
        event = EventFactory.createEvent(
                "event123",
                "Spring Boot Workshop",
                EventCategory.SEMINAR,
                LocalDateTime.of(2025, 5, 18, 10, 0),
                venue,
                123L,
                500.0
        );
    }

    @Test
    @Order(1)
    void create() {
        ResponseEntity<Event> postResponse = restTemplate.postForEntity(BASE_URL, event, Event.class);
        assertNotNull(postResponse.getBody());
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        event = postResponse.getBody();
        assertEquals("Spring Boot Workshop", event.getEventName());
        System.out.println("Created: " + event);
    }

    @Test
    @Order(2)
    void read() {
        ResponseEntity<Event> response = restTemplate.getForEntity(BASE_URL + "/" + event.getEventId(), Event.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(event.getEventId(), response.getBody().getEventId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Event updatedEvent = new Event.Builder()
                .copy(event)
                .setEventName("Spring Boot Advanced Workshop")
                .build();

        restTemplate.put(BASE_URL, updatedEvent);

        ResponseEntity<Event> response = restTemplate.getForEntity(BASE_URL + "/" + event.getEventId(), Event.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Spring Boot Advanced Workshop", response.getBody().getEventName());

        event = response.getBody();
        System.out.println("Updated: " + event);
    }

    @Test
    @Order(4)
    void getAll() {
        ResponseEntity<Event[]> response = restTemplate.getForEntity(BASE_URL, Event[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Events:");
        for (Event e : response.getBody()) {
            System.out.println(e);
        }
    }

    @Test
    @Order(5)
    void delete() {
        restTemplate.delete(BASE_URL + "/" + event.getEventId());

        ResponseEntity<Event> response = restTemplate.getForEntity(BASE_URL + "/" + event.getEventId(), Event.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted Event ID: " + event.getEventId());
    }
}
