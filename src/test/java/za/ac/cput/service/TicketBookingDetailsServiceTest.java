package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.factory.TicketBookingDetailsFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TicketBookingDetailsServiceTest {
    private TicketBookingDetailsService service;
    private TicketBookingDetails booking1;
    private TicketBookingDetails booking2;

    @BeforeEach
    void setUp() {
        service = new TicketBookingDetailsServiceImpl();
        booking1 = TicketBookingDetailsFactory.createTicketBookingDetails("B001", "S123", "E001", 2);
        booking2 = TicketBookingDetailsFactory.createTicketBookingDetails("B002", "S456", "E002", 4);

        service.create(booking1);
        service.create(booking2);
    }

    @Test
    void testCreate() {
        assertNotNull(booking1);
        assertNotNull(booking2);
    }

    @Test
    void testRead() {
        TicketBookingDetails read = service.read(booking1.getBookingID());
        assertEquals(booking1.getBookingID(), read.getBookingID());
    }

    @Test
    void testUpdate() {
        TicketBookingDetails updated = new TicketBookingDetails.Builder()
                .setBookingID(booking1.getBookingID())
                .setStudentID(booking1.getStudentID())
                .setEventID(booking1.getEventID())
                .setSeatsBooked(5)
                .build();

        service.update(updated);
        TicketBookingDetails read = service.read(booking1.getBookingID());
        assertEquals(5, read.getSeatsBooked());
    }

    @Test
    void testDelete() {
        boolean deleted = service.delete(booking2.getBookingID());
        assertTrue(deleted);
    }

    @Test
    void testGetAll() {
        Set<TicketBookingDetails> all = service.getAll();
        assertEquals(2, all.size());
    }
}
