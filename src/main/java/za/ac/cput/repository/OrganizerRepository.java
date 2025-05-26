package za.ac.cput.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Organizer;

import java.util.List;

@Repository
public interface OrganizerRepository extends CrudRepository<Organizer, Long> {
    List<Organizer> findByEvent(long eventId);
}
