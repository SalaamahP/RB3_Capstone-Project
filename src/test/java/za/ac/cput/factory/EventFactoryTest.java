/*
 * EventFactoryTest.java
 * Test class for EventFactory
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 18 May 2025
 */
package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Event;
import za.ac.cput.domain.Event.EventCategory;
import za.ac.cput.domain.Venue;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventFactoryTest {

    @Test
    void testCreateEventSuccess() {
        Venue venue = new Venue.Builder().setVenueId(1L).setVenueName("Main Hall").build();

        Event event = EventFactory.createEvent(
                "Concert",
                "Music event",
                EventCategory.SPORT,
                LocalDateTime.now(),
                venue,
                123L,
                500.0
        );

        assertNotNull(event);
        assertEquals("Concert", event.getEventName());
        assertEquals("Music event", event.getEventDescription());
        assertEquals(EventCategory.SPORT, event.getEventCategory());
        assertNotNull(event.getDateTime());
        assertEquals(venue, event.getVenue());
        assertEquals(123L, event.getUserId());
        assertEquals(500.0, event.getTicketPrice());
    }

    @Test
    void testCreateEventWithInvalidName() {
        Venue venue = new Venue.Builder().setVenueId(1L).setVenueName("Main Hall").build();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent(
                        "",
                        "Music event",
                        EventCategory.SPORT,
                        LocalDateTime.now(),
                        venue,
                        123L,
                        500.0
                ));
        assertEquals("Event name is required", exception.getMessage());
    }

    @Test
    void testCreateEventWithNullCategory() {
        Venue venue = new Venue.Builder().setVenueId(1L).setVenueName("Main Hall").build();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent(
                        "Concert",
                        "Music event",
                        null,
                        LocalDateTime.now(),
                        venue,
                        123L,
                        500.0
                ));
        assertEquals("Event category is required", exception.getMessage());
    }

    @Test
    void testCreateEventWithNegativeTicketPrice() {
        Venue venue = new Venue.Builder().setVenueId(1L).setVenueName("Main Hall").build();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent(
                        "Concert",
                        "Music event",
                        EventCategory.SPORT,
                        LocalDateTime.now(),
                        venue,
                        123L,
                        -10.0
                ));
        assertEquals("Ticket price cannot be negative", exception.getMessage());
    }
}
