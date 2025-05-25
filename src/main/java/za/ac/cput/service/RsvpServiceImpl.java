package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Rsvp;
import za.ac.cput.repository.RsvpRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RsvpServiceImpl implements RsvpService {

    private final RsvpRepository repository;

    @Autowired
    public RsvpServiceImpl(RsvpRepository repository) {
        this.repository = repository;
    }

    @Override
    public Rsvp save(Rsvp rsvp) {
        return repository.save(rsvp);
    }

    @Override
    public Optional<Rsvp> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Rsvp> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Rsvp> update(String id, Rsvp rsvp) {
        if (repository.existsById(id)) {
            Rsvp updatedRsvp = new Rsvp.Builder()
                    .setRsvpID(id)
                    .setStudent(rsvp.getStudent())
                    .setEvent(rsvp.getEvent())
                    .setStatus(rsvp.getStatus())
                    .build();
            return Optional.of(repository.save(updatedRsvp));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Rsvp> findByStudentId(String studentId) {
        return repository.findByStudent_StudentId(studentId);
    }

    @Override
    public List<Rsvp> findByEventId(String eventId) {
        return repository.findByEvent_EventId(eventId);
    }

    public void delete(String rsvpID) {
    }
}
