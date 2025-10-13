// Author: Jaedon Prince (230473474)
// Date: 18 May 2025
package za.ac.cput.factory;

import za.ac.cput.domain.TicketBookingDetails;

public class TicketBookingDetailsFactory {

    public static TicketBookingDetails createBooking(String studentID, String eventID) {
        if (studentID == null || eventID == null)
            throw new IllegalArgumentException("All fields must be provided and cannot be null.");

        return new TicketBookingDetails.Builder()
                .setStudentID(studentID)
                .setEventID(eventID)
                .build();
    }
}

