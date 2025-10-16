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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventFactoryTest {

    @Test
    void testCreateEventSuccess() {
        Event event = EventFactory.createEvent(
                "Concert",
                "Music event",
                EventCategory.SPORTS,
                LocalDateTime.now(),
                1L,
                123L,
                500.0
        );

        assertNotNull(event);
        assertEquals("Concert", event.getEventName());
        assertEquals("Music event", event.getEventDescription());
        assertEquals(EventCategory.SPORTS, event.getEventCategory());
        assertNotNull(event.getDateTime());
        assertEquals(1L, event.getVenueId());
        assertEquals(123L, event.getUserId());
        assertEquals(500.0, event.getTicketPrice());
    }

    @Test
    void testCreateEventWithInvalidName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent(
                        "",
                        "Music event",
                        EventCategory.SPORTS,
                        LocalDateTime.now(),
                        1L,
                        123L,
                        500.0
                ));
        assertEquals("Event name is required", exception.getMessage());
    }

    @Test
    void testCreateEventWithNullCategory() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent(
                        "Concert",
                        "Music event",
                        null,
                        LocalDateTime.now(),
                        1L,
                        123L,
                        500.0
                ));
        assertEquals("Event category is required", exception.getMessage());
    }

    @Test
    void testCreateEventWithNegativeTicketPrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent(
                        "Concert",
                        "Music event",
                        EventCategory.SPORTS,
                        LocalDateTime.now(),
                        1L,
                        123L,
                        -10.0
                ));
        assertEquals("Ticket price cannot be negative", exception.getMessage());
    }

    @Test
    void testCreateEventWithInvalidVenueId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent(
                        "Concert",
                        "Music event",
                        EventCategory.SPORTS,
                        LocalDateTime.now(),
                        0L,
                        123L,
                        500.0
                ));
        assertEquals("Valid venue ID is required", exception.getMessage());
    }

    @Test
    void testCreateEventWithInvalidUserId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent(
                        "Concert",
                        "Music event",
                        EventCategory.SPORTS,
                        LocalDateTime.now(),
                        1L,
                        -1L,
                        500.0
                ));
        assertEquals("Valid user ID is required", exception.getMessage());
    }

    @Test
    void testCreateEventWithNullDateTime() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent(
                        "Concert",
                        "Music event",
                        EventCategory.SPORTS,
                        null,
                        1L,
                        123L,
                        500.0
                ));
        assertEquals("Event date and time are required", exception.getMessage());
    }

    @Test
    void testCreateEventWithNullDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent(
                        "Concert",
                        null,
                        EventCategory.SPORTS,
                        LocalDateTime.now(),
                        1L,
                        123L,
                        500.0
                ));
        assertEquals("Event description is required", exception.getMessage());
    }
}
