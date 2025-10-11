package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Event;
import za.ac.cput.domain.Event.EventCategory;
import za.ac.cput.domain.Venue;
import za.ac.cput.factory.EventFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private VenueService venueService;

    private static Event event;
    private static Venue venue;

    @BeforeAll
    static void setUp(@Autowired VenueService venueService) {
        // Create and save a venue before testing events
        venue = new Venue.Builder()
                .setVenueName("CPUT Hall")
                .setVenueLocation("Cape Town")
                .setCapacity(100)
                .build();

        venue = venueService.create(venue);

        // Now safely create an event with a valid venue
        event = EventFactory.createEvent(
                "Spring Boot Workshop",
                "Learn Spring Boot Advanced Concepts",
                EventCategory.SEMINAR,
                LocalDateTime.of(2025, 5, 18, 10, 0),
                venue,
                123L,
                500.0
        );
    }

    @Test
    @Order(1)
    void add() {
        Event created = eventService.create(event);
        assertNotNull(created.getEventId());
        assertEquals("Spring Boot Workshop", created.getEventName());
        event = created;
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void read() {
        Event readEvent = eventService.read(event.getEventId());
        assertNotNull(readEvent);
        assertEquals(event.getEventId(), readEvent.getEventId());
        System.out.println("Read: " + readEvent);
    }

    @Test
    @Order(3)
    void update() {
        Event updatedEvent = new Event.Builder()
                .copy(event)
                .setEventName("Spring Boot Advanced Workshop")
                .build();

        Event updated = eventService.update(updatedEvent);
        assertEquals("Spring Boot Advanced Workshop", updated.getEventName());
        event = updated;
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Event> events = eventService.getAll();
        assertNotNull(events);
        assertFalse(events.isEmpty());
        System.out.println("All Events: " + events);
    }

    @Test
    @Order(5)
    void delete() {
        boolean deleted = eventService.delete(event.getEventId());
        assertTrue(deleted);
        assertNull(eventService.read(event.getEventId()));
        System.out.println("Deleted Event ID: " + event.getEventId());
    }
}
