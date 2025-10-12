/*package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.TicketBookingDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TicketBookingDetailsServiceTest {

    @Autowired
    private TicketBookingDetailsService service;

    private TicketBookingDetails booking1;
    private TicketBookingDetails booking2;

    @BeforeEach
    void setUp() {
        booking1 = new TicketBookingDetails.Builder()
                .setBookingID("B001")
                .setStudentId("S123")
                .setEventId("E456")
                .setNumberOfTickets(2)
                .build();

        booking2 = new TicketBookingDetails.Builder()
                .setBookingID("B002")
                .setStudentId("S789")
                .setEventId("E999")
                .setNumberOfTickets(4)
                .build();

        service.create(booking1);
        service.create(booking2);
    }

    @Test
    void testCreateAndRead() {
        TicketBookingDetails saved = service.create(booking1);
        assertNotNull(saved);
        TicketBookingDetails found = service.read("B001");
        assertEquals("S123", found.getStudentId());
    }

    @Test
    void testUpdate() {
        TicketBookingDetails updated = new TicketBookingDetails.Builder()
                .setBookingID("B001")
                .setStudentId("S123")
                .setEventId("E456")
                .setNumberOfTickets(5) // changed
                .build();

        TicketBookingDetails result = service.update(updated);
        assertNotNull(result);
        assertEquals(5, result.getNumberOfTickets());
    }

    @Test
    void testDelete() {
        boolean deleted = service.delete("B002");
        assertTrue(deleted);
        assertNull(service.read("B002"));
    }

    @Test
    void testGetAll() {
        List<TicketBookingDetails> all = service.getAll();
        assertFalse(all.isEmpty());
    }
}
*/
