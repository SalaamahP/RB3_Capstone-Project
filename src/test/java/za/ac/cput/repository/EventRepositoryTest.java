package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.Event;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void testSaveAndFindById() {
        Event event = new Event.Builder()
                .setEventId(Long.parseLong("event123"))
                .setEventName("Spring Boot Workshop")
                .setDateTime(LocalDateTime.parse("2025-05-18"))
                .build();

        Event savedEvent = eventRepository.save(event);
        assertNotNull(savedEvent);
        assertEquals("event123", savedEvent.getEventId());

        Event foundEvent = eventRepository.findById("event123").orElse(null);
        assertNotNull(foundEvent);
        assertEquals("event123", foundEvent.getEventId());
    }

    @Test
    void testDeleteById() {
        Event event = new Event.Builder()
                .setEventId(Long.parseLong("event123"))
                .setEventName("Spring Boot Workshop")
                .setDateTime(LocalDateTime.parse("2025-05-18"))
                .build();

        eventRepository.save(event);
        eventRepository.deleteById("event123");

        assertFalse(eventRepository.existsById("event123"));
    }
}