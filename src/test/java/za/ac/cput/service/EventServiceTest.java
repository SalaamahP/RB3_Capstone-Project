package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Event;
import za.ac.cput.domain.Event.EventCategory;
import za.ac.cput.factory.EventFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventServiceTest {

    @Autowired
    private EventService eventService;

    private static Event event;

    @BeforeAll
    static void setUp() {
        event = EventFactory.createEvent(
                "Spring Boot Workshop",
                "A workshop on Spring Boot basics",
                EventCategory.WORKSHOP,
                LocalDateTime.of(2025, 5, 18, 10, 0),
                1L,
                123L,
                500.0
        );
    }

    @Test
    @Order(1)
    @Transactional
    @Rollback(false) // keep the data for following tests
    void testCreate() {
        Event created = eventService.create(event);
        assertNotNull(created);
        assertTrue(created.getEventId() > 0);
        event = created; // save for later tests
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void testRead() {
        Event found = eventService.read(event.getEventId());
        assertNotNull(found);
        assertEquals(event.getEventName(), found.getEventName());
        System.out.println("Read: " + found);
    }

    @Test
    @Order(3)
    void testUpdate() {
        Event updatedEvent = new Event.Builder()
                .copy(event)
                .setEventName("Spring Boot Advanced Workshop")
                .build();

        Event updated = eventService.update(updatedEvent);
        assertNotNull(updated);
        assertEquals("Spring Boot Advanced Workshop", updated.getEventName());
        event = updated; // save for next tests
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void testGetAll() {
        List<Event> events = eventService.getAll();
        assertNotNull(events);
        assertTrue(events.size() > 0);
        System.out.println("All Events:");
        events.forEach(System.out::println);
    }

    @Test
    @Order(5)
    void testDelete() {
        boolean deleted = eventService.delete(event.getEventId());
        assertTrue(deleted);

        Event shouldBeNull = eventService.read(event.getEventId());
        assertNull(shouldBeNull);
        System.out.println("Deleted Event ID: " + event.getEventId());
    }
}
