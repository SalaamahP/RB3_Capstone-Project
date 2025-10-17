// Author: Jaedon Prince (230473474)
// Date: 18 May 2025
package za.ac.cput.factory;

import za.ac.cput.domain.TicketBookingDetails;
import java.time.LocalDateTime;

public class TicketBookingDetailsFactory {

    public static TicketBookingDetails createBooking(String studentID, String eventID) {
        if (studentID == null || studentID.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (eventID == null || eventID.trim().isEmpty()) {
            throw new IllegalArgumentException("Event ID cannot be null or empty");
        }

        return new TicketBookingDetails.Builder()
                .setStudentID(studentID)
                .setEventID(eventID)
                .setBookingDate(LocalDateTime.now())
                .build();
    }

    public static TicketBookingDetails createBookingWithDate(String studentID, String eventID, LocalDateTime bookingDate) {
        if (studentID == null || studentID.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (eventID == null || eventID.trim().isEmpty()) {
            throw new IllegalArgumentException("Event ID cannot be null or empty");
        }
        if (bookingDate == null) {
            throw new IllegalArgumentException("Booking date cannot be null");
        }

        return new TicketBookingDetails.Builder()
                .setStudentID(studentID)
                .setEventID(eventID)
                .setBookingDate(bookingDate)
                .build();
    }
}
