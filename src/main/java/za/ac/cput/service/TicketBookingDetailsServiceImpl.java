//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
/*package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Notification;
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.repository.TicketBookingDetailsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TicketBookingDetailsServiceImpl implements TicketBookingDetailsService {

    private final TicketBookingDetailsRepository repository;

    @Autowired
    public TicketBookingDetailsServiceImpl(TicketBookingDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public TicketBookingDetails create(TicketBookingDetails details) {
        return repository.save(details);
    }

    @Override
    public TicketBookingDetails read(String id) {
        Optional<TicketBookingDetails> details = repository.findById(id);
        return details.orElse(null);
    }

    @Override
    public TicketBookingDetails update(TicketBookingDetails details) {
        if (repository.existsById(details.getBookingID())) {
            return repository.save(details);
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<TicketBookingDetails> getAll() {
        return repository.findAll();
    }

    @Override
    public TicketBookingDetails save(TicketBookingDetails details) {
        return repository.save(details);
    }
}
*/