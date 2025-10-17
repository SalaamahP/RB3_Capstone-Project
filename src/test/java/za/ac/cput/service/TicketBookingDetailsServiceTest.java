//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.factory.TicketBookingDetailsFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketBookingDetailsServiceTest {

    @Autowired
    private TicketBookingDetailsService service;

    private static TicketBookingDetails booking1;
    private static TicketBookingDetails booking2;

    @BeforeAll
    static void setUp() {
        booking1 = TicketBookingDetailsFactory.createBooking(
                "STU001",
                "EVT001"
        );

        booking2 = TicketBookingDetailsFactory.createBooking(
                "STU002",
                "EVT002"
        );
    }

    @Test
    @Order(1)
    void testCreate() {
        TicketBookingDetails created = service.create(booking1);
        assertNotNull(created);
        assertNotNull(created.getBookingID());
        assertEquals("STU001", created.getStudentID());
        assertEquals("EVT001", created.getEventID());
        assertNotNull(created.getBookingDate());
        booking1 = created;
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void testRead() {
        TicketBookingDetails created = service.create(booking1);
        TicketBookingDetails read = service.read(created.getBookingID());
        assertNotNull(read);
        assertEquals(created.getBookingID(), read.getBookingID());
        assertEquals("STU001", read.getStudentID());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void testUpdate() {
        TicketBookingDetails created = service.create(booking1);

        TicketBookingDetails updated = new TicketBookingDetails.Builder()
                .setBookingID(created.getBookingID())
                .setStudentID("STU999")
                .setEventID(created.getEventID())
                .setBookingDate(created.getBookingDate())
                .build();

        TicketBookingDetails result = service.update(updated);
        assertNotNull(result);
        assertEquals("STU999", result.getStudentID());
        assertEquals(created.getBookingID(), result.getBookingID());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void testUpdateNonExistent() {
        TicketBookingDetails nonExistent = new TicketBookingDetails.Builder()
                .setBookingID(99999L)
                .setStudentID("STU999")
                .setEventID("EVT999")
                .build();

        TicketBookingDetails result = service.update(nonExistent);
        assertNull(result);
        System.out.println("Update non-existent returned null as expected");
    }

    @Test
    @Order(5)
    void testGetAll() {
        service.create(booking1);
        service.create(booking2);

        List<TicketBookingDetails> bookings = service.getAll();
        assertNotNull(bookings);
        assertTrue(bookings.size() >= 2);
        System.out.println("All Bookings:");
        bookings.forEach(System.out::println);
    }

    @Test
    @Order(6)
    void testDelete() {
        TicketBookingDetails created = service.create(booking1);
        Long id = created.getBookingID();

        boolean deleted = service.delete(id);
        assertTrue(deleted);

        TicketBookingDetails read = service.read(id);
        assertNull(read);
        System.out.println("Deleted booking with ID: " + id);
    }

    @Test
    @Order(7)
    void testDeleteNonExistent() {
        boolean deleted = service.delete(99999L);
        assertFalse(deleted);
        System.out.println("Delete non-existent returned false as expected");
    }

    @Test
    @Order(8)
    void testReadNonExistent() {
        TicketBookingDetails read = service.read(99999L);
        assertNull(read);
        System.out.println("Read non-existent returned null as expected");
    }
}

