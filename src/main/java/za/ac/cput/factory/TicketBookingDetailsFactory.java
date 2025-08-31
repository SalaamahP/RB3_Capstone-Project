// Author: Jaedon Prince (230473474)
// Date: 18 May 2025
package za.ac.cput.factory;

import za.ac.cput.domain.TicketBookingDetails;

public class TicketBookingDetailsFactory {

    public static TicketBookingDetails createTicketBookingDetails(
            String bookingID, String eventId, String studentId, int numberOfTickets) {

        return new TicketBookingDetails.Builder()
                .setBookingID(bookingID)
                .setEventId(eventId)
                .setStudentId(studentId)
                .setNumberOfTickets(numberOfTickets)
                .build();
    }
}

