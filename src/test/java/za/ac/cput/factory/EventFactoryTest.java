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
// Test case to verify the successful creation of an Event object
    @Test
    void testCreateEventSuccess() {
        Event event = EventFactory.createEvent("Concert", "Music event", EventCategory.SPORT,
                LocalDateTime.now(), 1L, 123L, 500.0);
        assertNotNull(event);
        assertEquals("Concert", event.getEventName());
        assertEquals("Music event", event.getEventDescription());
        assertEquals(EventCategory.SPORT, event.getEventCategory());
        assertNotNull(event.getDateTime());
        assertEquals(1L, event.getVenueId());
        assertEquals(123L, event.getUserId());
        assertEquals(500.0, event.getTicketPrice());
    }
// Test case to verify the exception thrown when an invalid event name is provided
    @Test
    void testCreateEventWithInvalidName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent("", "Music event", EventCategory.SPORT,
                        LocalDateTime.now(), 1L, 123L, 500.0));
        assertEquals("Event name is required", exception.getMessage());
    }
// Test case to verify the exception thrown when an invalid event category is provided
    @Test
    void testCreateEventWithNullCategory() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent("Concert", "Music event", null,
                        LocalDateTime.now(), 1L, 123L, 500.0));
        assertEquals("Event category is required", exception.getMessage());
    }
// Test case to verify the exception thrown when ticket price is negative
    @Test
    void testCreateEventWithNegativeTicketPrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                EventFactory.createEvent("Concert", "Music event", EventCategory.SPORT,
                        LocalDateTime.now(), 1L, 123L, -100.0));
        assertEquals("Ticket price cannot be negative", exception.getMessage());
    }
}