package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Venue;
import za.ac.cput.repository.VenueRepository;

import java.util.List;

@Service
public class VenueService implements IVenueService {
    private static IVenueService service;

    VenueRepository repository;
    @Autowired VenueService(VenueRepository repository) {
        this.repository = repository;
    }
    @Override
    public Venue create(Venue venue) {
        return repository.save(venue);
    }

    @Override
    public Venue read(Long id) {
       return this.repository.findById(id).orElse(null);
    }

    @Override
    public Venue update(Venue venue) {
        return this.repository.save(venue);
    }

    @Override
    public boolean delete(Long id) {
       this.repository.deleteById(id);
       return true;
    }

    @Override
    public List<Venue> getAll() {
        return this.repository.findAll();
    }
}
