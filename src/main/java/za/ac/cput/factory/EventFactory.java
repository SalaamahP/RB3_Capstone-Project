/*
 * EventFactory.java
 * Factory class for Event
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 18 May 2025
 */
package za.ac.cput.factory;

import za.ac.cput.domain.Event;
import za.ac.cput.domain.Event.EventCategory;
import za.ac.cput.domain.Venue;

import java.time.LocalDateTime;

public class EventFactory {

    public static Event createEvent(String eventName, String eventDescription,
                                    EventCategory eventCategory, LocalDateTime dateTime,
                                    Venue venue, long userId, double ticketPrice) {
        if (eventName == null || eventName.isEmpty()) {
            throw new IllegalArgumentException("Event name is required");
        }
        if (eventDescription == null || eventDescription.isEmpty()) {
            throw new IllegalArgumentException("Event description is required");
        }
        if (eventCategory == null) {
            throw new IllegalArgumentException("Event category is required");
        }
        if (dateTime == null) {
            throw new IllegalArgumentException("Event date and time are required");
        }
        if (venue == null) {
            throw new IllegalArgumentException("Valid venue is required");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("Valid user ID is required");
        }
        if (ticketPrice < 0) {
            throw new IllegalArgumentException("Ticket price cannot be negative");
        }

        return new Event.Builder()
                .setEventName(eventName)
                .setEventDescription(eventDescription)
                .setEventCategory(eventCategory)
                .setDateTime(dateTime)
                .setVenueId(venue)
                .setUserId(userId)
                .setTicketPrice(ticketPrice)
                .build();
    }
}
