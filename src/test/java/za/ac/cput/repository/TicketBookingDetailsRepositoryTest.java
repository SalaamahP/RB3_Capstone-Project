//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.factory.TicketBookingDetailsFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TicketBookingDetailsRepositoryTest {

    @Autowired
    private TicketBookingDetailsRepository repository;

    @Test
    void crudOperations() {
        TicketBookingDetails booking = TicketBookingDetailsFactory.createBooking("S002", "E002");
        TicketBookingDetails saved = repository.save(booking);
        assertNotNull(saved.getBookingID());

        TicketBookingDetails read = repository.findById(saved.getBookingID()).orElse(null);
        assertNotNull(read);

        saved = new TicketBookingDetails.Builder()
                .setBookingID(saved.getBookingID())
                .setStudentID("S002")
                .setEventID("E002")
                .build();

        repository.save(saved);
        TicketBookingDetails updated = repository.findById(saved.getBookingID()).orElse(null);
        assertEquals("S002", updated.getStudentID());

        List<TicketBookingDetails> all = repository.findAll();
        assertTrue(all.size() >= 1);

        repository.deleteById(saved.getBookingID());
        assertFalse(repository.existsById(saved.getBookingID()));
    }
}
