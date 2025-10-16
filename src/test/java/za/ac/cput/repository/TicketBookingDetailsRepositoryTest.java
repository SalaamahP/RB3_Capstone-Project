
//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.factory.TicketBookingDetailsFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketBookingDetailsRepositoryTest {

    @Autowired
    private TicketBookingDetailsRepository repository;

    @Test
    @Order(1)
    void testSave() {
        TicketBookingDetails booking = TicketBookingDetailsFactory.createBooking(
                "STU001",
                "EVT001"
        );

        TicketBookingDetails saved = repository.save(booking);
        assertNotNull(saved);
        assertNotNull(saved.getBookingID());
        assertEquals("STU001", saved.getStudentID());
        assertEquals("EVT001", saved.getEventID());
        assertNotNull(saved.getBookingDate());
        System.out.println("Saved: " + saved);
    }

    @Test
    @Order(2)
    void testFindById() {
        TicketBookingDetails booking = TicketBookingDetailsFactory.createBooking(
                "STU002",
                "EVT002"
        );

        TicketBookingDetails saved = repository.save(booking);
        Optional<TicketBookingDetails> found = repository.findById(saved.getBookingID());

        assertTrue(found.isPresent());
        assertEquals(saved.getBookingID(), found.get().getBookingID());
        assertEquals("STU002", found.get().getStudentID());
        System.out.println("Found: " + found.get());
    }

    @Test
    @Order(3)
    void testFindAll() {
        // Clear any existing data first
        repository.deleteAll();

        TicketBookingDetails booking1 = TicketBookingDetailsFactory.createBooking(
                "STU003",
                "EVT003"
        );

        TicketBookingDetails booking2 = TicketBookingDetailsFactory.createBooking(
                "STU004",
                "EVT004"
        );

        repository.save(booking1);
        repository.save(booking2);

        List<TicketBookingDetails> bookings = repository.findAll();
        assertNotNull(bookings);
        assertTrue(bookings.size() >= 2);
        System.out.println("All Bookings:");
        bookings.forEach(System.out::println);
    }

    @Test
    @Order(4)
    void testUpdate() {
        TicketBookingDetails booking = TicketBookingDetailsFactory.createBooking(
                "STU005",
                "EVT005"
        );

        TicketBookingDetails saved = repository.save(booking);

        // Create a new instance with updated data
        TicketBookingDetails updated = new TicketBookingDetails.Builder()
                .setBookingID(saved.getBookingID())
                .setStudentID("STU999")
                .setEventID(saved.getEventID())
                .setBookingDate(saved.getBookingDate())
                .build();

        TicketBookingDetails result = repository.save(updated);
        assertNotNull(result);
        assertEquals("STU999", result.getStudentID());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(5)
    void testDelete() {
        TicketBookingDetails booking = TicketBookingDetailsFactory.createBooking(
                "STU006",
                "EVT006"
        );

        TicketBookingDetails saved = repository.save(booking);
        Long id = saved.getBookingID();

        repository.deleteById(id);

        Optional<TicketBookingDetails> found = repository.findById(id);
        assertFalse(found.isPresent());
        System.out.println("Deleted booking with ID: " + id);
    }

    @Test
    @Order(6)
    void testExistsById() {
        TicketBookingDetails booking = TicketBookingDetailsFactory.createBooking(
                "STU007",
                "EVT007"
        );

        TicketBookingDetails saved = repository.save(booking);
        assertTrue(repository.existsById(saved.getBookingID()));

        repository.deleteById(saved.getBookingID());
        assertFalse(repository.existsById(saved.getBookingID()));
    }
}

