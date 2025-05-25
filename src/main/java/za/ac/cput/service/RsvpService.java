package za.ac.cput.service;

import za.ac.cput.domain.Rsvp;

import java.util.List;
import java.util.Optional;

public interface RsvpService {
    Rsvp save(Rsvp rsvp);
    List<Rsvp> findAll();

    Optional<Rsvp> findById(String id);
    Optional<Rsvp> update(String id, Rsvp rsvp);
    boolean deleteById(String id);
    List<Rsvp> findByStudentId(String studentId);

    List<Rsvp> findByEventId(String eventId);
}
