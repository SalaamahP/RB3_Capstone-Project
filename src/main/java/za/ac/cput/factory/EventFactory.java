/*
 * EventFactory.java
 * Factory class for Event
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 18 May 2025
 */

package za.ac.cput.factory;

import za.ac.cput.domain.Event;
import za.ac.cput.domain.Event.EventCategory;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;

public class EventFactory {

    public static Event createEvent(String eventName, String eventDescription,
                                    EventCategory eventCategory, LocalDateTime dateTime,
                                    long venueId, long userId, double ticketPrice) {

        // ---------- String validations ----------
        if (Helper.isNullOrEmpty(eventName))
            throw new IllegalArgumentException("Event name is required");

        if (Helper.isNullOrEmpty(eventDescription))
            throw new IllegalArgumentException("Event description is required");

        // ---------- Object validations ----------
        if (!Helper.isValidEventCategory(eventCategory))
            throw new IllegalArgumentException("Event category is required");

        if (!Helper.isValidDateTime(dateTime))
            throw new IllegalArgumentException("Event date and time are required");

        // ---------- Numeric validations ----------
        if (!Helper.isValidVenueId(venueId))
            throw new IllegalArgumentException("Valid venue ID is required");

        if (!Helper.isValidUserId(userId))
            throw new IllegalArgumentException("Valid user ID is required");

        if (!Helper.isValidTicketPrice(ticketPrice))
            throw new IllegalArgumentException("Ticket price cannot be negative");

        // ---------- Build Event ----------
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
