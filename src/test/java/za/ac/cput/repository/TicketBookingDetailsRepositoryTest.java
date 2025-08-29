//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.TicketBookingDetails;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TicketBookingDetailsRepositoryTest {

    @Autowired
    private TicketBookingDetailsRepository repository;

    @Test
    void saveAndFind() {
        TicketBookingDetails b = new TicketBookingDetails.Builder()
                .setBookingID("B200")
                .setStudentId("S200")
                .setEventId("E200")
                .setBookingDate(LocalDateTime.now())
                .build();

        repository.save(b);

        assertTrue(repository.findById("B200").isPresent());
        assertEquals("E200", repository.findById("B200").get().getEventId());
    }
}
