//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.repository.TicketBookingDetailsRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TicketBookingDetailsRepositoryTest {

    @Autowired
    private TicketBookingDetailsRepository repository;

    @Test
    void testCreateReadDelete() {
        TicketBookingDetails booking = new TicketBookingDetails.Builder()
                .setUserId(123L)
                .setEventId("EV001")
                .setQuantity(2)
                .setTotal(500.00)
                .setPaymentSelection(TicketBookingDetails.PaymentOption.CASH)
                .setStatus(TicketBookingDetails.Status.PENDING)
                .build();

        TicketBookingDetails saved = repository.save(booking);
        Optional<TicketBookingDetails> fetched = repository.findById(saved.getBookingId());

        assertTrue(fetched.isPresent());
        assertEquals(saved.getBookingId(), fetched.get().getBookingId());

        repository.delete(saved);
        assertFalse(repository.existsById(saved.getBookingId()));
    }
}