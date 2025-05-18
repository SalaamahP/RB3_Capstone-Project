// Author: Jaedon Prince (230473474)
// Date: 18 May 2025

package za.ac.cput.Factory;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.TicketBookingDetails;

import static org.junit.jupiter.api.Assertions.*;

class TicketBookingDetailsFactoryTest {

    @Test
    @DisplayName("Factory creates non-null booking with correct fields")
    void testCreateBooking() {
        long userId = 500L;
        String eventId = "EVT789";
        int quantity = 3;
        double total = 750.0;
        TicketBookingDetails.PaymentOption payment = TicketBookingDetails.PaymentOption.DEPOSIT;
        TicketBookingDetails.Status status = TicketBookingDetails.Status.PENDING;

        TicketBookingDetails booking = TicketBookingDetailsFactory
                .createBooking(userId, eventId, quantity, total, payment, status);

        assertNotNull(booking, "Factory returned null");
        assertNotNull(booking.getBookingId(), "Booking ID should be auto-generated and non-null");
        assertEquals(userId, booking.getUserId());
        assertEquals(eventId, booking.getEventId());
        assertEquals(quantity, booking.getQuantity());
        assertEquals(total, booking.getTotal());
        assertEquals(payment, booking.getPaymentSelection());
        assertEquals(status, booking.getStatus());
        assertNotNull(booking.getDateBooked(), "DateBooked should default to now");
    }

    @Test
    @DisplayName("Factory produces unique IDs for multiple creations")
    void testUniqueIds() {
        TicketBookingDetails b1 = TicketBookingDetailsFactory
                .createBooking(1L, "X", 1, 100.0,
                        TicketBookingDetails.PaymentOption.CASH,
                        TicketBookingDetails.Status.CONFIRMED);
        TicketBookingDetails b2 = TicketBookingDetailsFactory
                .createBooking(2L, "Y", 2, 200.0,
                        TicketBookingDetails.PaymentOption.CASH,
                        TicketBookingDetails.Status.CONFIRMED);

        assertNotEquals(b1.getBookingId(), b2.getBookingId(),
                "Two bookings should not share the same ID");
    }
}
