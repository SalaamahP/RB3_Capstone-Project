/*package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.Event;
import za.ac.cput.domain.Venue;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void testSaveAndFindById() {
        // You do NOT set eventId manually because it’s auto-generated
        Event event = new Event.Builder()
                .setEventName("Spring Boot Workshop")
                .setEventDescription("Learn Spring Boot basics")
                .setEventCategory(Event.EventCategory.SEMINAR)
                .setDateTime(LocalDateTime.of(2025, 5, 18, 10, 0))
                .setVenueId(new Venue()) // Optional: can mock or use @DataJpaTest with VenueRepository
                .setUserId(1L)
                .setTicketPrice(200.0)
                .build();

        Event savedEvent = eventRepository.save(event);
        assertNotNull(savedEvent);
        assertTrue(savedEvent.getEventId() > 0); // auto-generated ID should be greater than 0

        Event foundEvent = eventRepository.findById(savedEvent.getEventId()).orElse(null);
        assertNotNull(foundEvent);
        assertEquals(savedEvent.getEventName(), foundEvent.getEventName());
    }

    @Test
    void testDeleteById() {
        Event event = new Event.Builder()
                .setEventName("Delete Test Event")
                .setEventDescription("Testing delete functionality")
                .setEventCategory(Event.EventCategory.OTHER)
                .setDateTime(LocalDateTime.of(2025, 5, 18, 10, 0))
                .setVenueId(new Venue())
                .setUserId(1L)
                .setTicketPrice(150.0)
                .build();

        Event savedEvent = eventRepository.save(event);
        long id = savedEvent.getEventId();

        eventRepository.deleteById(id);
        assertFalse(eventRepository.existsById(id));
    }
}
*/