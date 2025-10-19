// Author: Jaedon Prince (230473474)
// Date: 18 May 2025
package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.TicketBookingDetails;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TicketBookingDetailsFactoryTest {

    @Test
    void testCreateBookingSuccess() {
        TicketBookingDetails booking = TicketBookingDetailsFactory.createBooking(
                "STU001",
                "EVT001"
        );

        assertNotNull(booking);
        assertEquals("STU001", booking.getStudentID());
        assertEquals("EVT001", booking.getEventID());
        assertNotNull(booking.getBookingDate());
        System.out.println("Created Booking: " + booking);
    }

    @Test
    void testCreateBookingWithDate() {
        LocalDateTime bookingDate = LocalDateTime.of(2025, 5, 18, 14, 30);
        TicketBookingDetails booking = TicketBookingDetailsFactory.createBookingWithDate(
                "STU002",
                "EVT002",
                bookingDate
        );

        assertNotNull(booking);
        assertEquals("STU002", booking.getStudentID());
        assertEquals("EVT002", booking.getEventID());
        assertEquals(bookingDate, booking.getBookingDate());
        System.out.println("Created Booking with Date: " + booking);
    }

    @Test
    void testCreateBookingWithNullStudentID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                TicketBookingDetailsFactory.createBooking(null, "EVT001")
        );
        assertEquals("Student ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateBookingWithEmptyStudentID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                TicketBookingDetailsFactory.createBooking("   ", "EVT001")
        );
        assertEquals("Student ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateBookingWithNullEventID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                TicketBookingDetailsFactory.createBooking("STU001", null)
        );
        assertEquals("Event ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateBookingWithEmptyEventID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                TicketBookingDetailsFactory.createBooking("STU001", "")
        );
        assertEquals("Event ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateBookingWithDateNullDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                TicketBookingDetailsFactory.createBookingWithDate("STU001", "EVT001", null)
        );
        assertEquals("Booking date cannot be null", exception.getMessage());
    }

    @Test
    void testCreateBookingWithDateAllNullFields() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                TicketBookingDetailsFactory.createBookingWithDate(null, null, null)
        );
        assertEquals("Student ID cannot be null or empty", exception.getMessage());
    }
}