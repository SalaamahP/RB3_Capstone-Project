//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.service;

import org.springframework.stereotype.Service;
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.repository.TicketBookingDetailsRepository;
import za.ac.cput.service.TicketBookingDetailsService;

import java.util.List;
import java.util.Optional;

@Service
public class TicketBookingDetailsServiceImpl implements TicketBookingDetailsService {

    private final TicketBookingDetailsRepository repository;

    public TicketBookingDetailsServiceImpl(TicketBookingDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public TicketBookingDetails create(TicketBookingDetails details) {
        return repository.save(details);
    }

    @Override
    public TicketBookingDetails read(String bookingId) {
        Optional<TicketBookingDetails> details = repository.findById(bookingId);
        return details.orElse(null);
    }

    @Override
    public TicketBookingDetails update(TicketBookingDetails details) {
        if (repository.existsById(details.getBookingId())) {
            return repository.save(details);
        }
        return null;
    }

    @Override
    public boolean delete(String bookingId) {
        if (repository.existsById(bookingId)) {
            repository.deleteById(bookingId);
            return true;
        }
        return false;
    }

    @Override
    public List<TicketBookingDetails> getAll() {
        return repository.findAll();
    }
}