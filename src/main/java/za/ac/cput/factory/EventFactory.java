/*
 * EventFactory.java
 * Factory class for Event
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 18 May 2025
 */
package za.ac.cput.factory;

import za.ac.cput.domain.Event;
import za.ac.cput.domain.Event.EventCategory;

import java.time.LocalDateTime;

public class EventFactory {

    public static Event createEvent(String eventName, String eventDescription, EventCategory eventCategory,
                                    LocalDateTime dateTime, long venueId, long userId, double ticketPrice) {
        // Validate eventName
        if (eventName == null || eventName.isEmpty()) {
            throw new IllegalArgumentException("Event name is required");
        }
        // Validate eventDescription
        if (eventDescription == null || eventDescription.isEmpty()) {
            throw new IllegalArgumentException("Event description is required");
        }
        // Validate eventCategory
        if (eventCategory == null) {
            throw new IllegalArgumentException("Event category is required");
        }
        // Validate dateTime
        if (dateTime == null) {
            throw new IllegalArgumentException("Event date and time are required");
        }
        // Validate venueId
        if (venueId <= 0) {
            throw new IllegalArgumentException("Valid venue ID is required");
        }
        // Validate userId
        if (userId <= 0) {
            throw new IllegalArgumentException("Valid user ID is required");
        }
        // Validate ticketPrice
        if (ticketPrice < 0) {
            throw new IllegalArgumentException("Ticket price cannot be negative");
        }

        // Build and return the Event object
        return new Event.Builder()
                .setEventName(eventName)
                .setEventDescription(eventDescription)
                .setEventCategory(eventCategory)
                .setDateTime(dateTime)
                .setVenueId(venueId)
                .setUserId(userId)
                .setTicketPrice(ticketPrice)
                .build();
    }
}