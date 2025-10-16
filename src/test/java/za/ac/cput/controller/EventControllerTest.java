package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Event;
import za.ac.cput.domain.Event.EventCategory;
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
        BASE_URL = "http://localhost:" + port + "/SEMS/event";
    }

    @BeforeAll
    public static void setUp() {
        event = EventFactory.createEvent(
                "Spring Boot Workshop",
                "A workshop on Spring Boot basics",
                EventCategory.WORKSHOP,
                LocalDateTime.of(2025, 5, 18, 10, 0),
                1L,   // valid venueId
                123L, // valid userId
                500.0
        );
    }

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Event> postResponse = restTemplate.postForEntity(url, event, Event.class);
        assertNotNull(postResponse.getBody());

        event = postResponse.getBody();
        assertEquals("Spring Boot Workshop", event.getEventName());
        System.out.println("Created: " + event);
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + event.getEventId();
        ResponseEntity<Event> response = restTemplate.getForEntity(url, Event.class);
        assertEquals(event.getEventId(), Objects.requireNonNull(response.getBody()).getEventId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Event updatedEvent = new Event.Builder()
                .copy(event)
                .setEventName("Spring Boot Advanced Workshop")
                .build();

        String url = BASE_URL + "/update";
        restTemplate.put(url, updatedEvent);

        ResponseEntity<Event> response = restTemplate.getForEntity(BASE_URL + "/read/" + event.getEventId(), Event.class);
        assertNotNull(response.getBody());
        assertEquals("Spring Boot Advanced Workshop", response.getBody().getEventName());
        System.out.println("Updated: " + response.getBody());

        event = response.getBody();
    }

    @Test
    @Order(4)
    void getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Event[]> response = restTemplate.getForEntity(url, Event[].class);
        assertNotNull(response.getBody());
        System.out.println("All Events:");
        for (Event e : response.getBody()) {
            System.out.println(e);
        }
    }
}

