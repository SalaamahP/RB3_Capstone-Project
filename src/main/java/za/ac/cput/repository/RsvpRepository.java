package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Rsvp;

import java.util.List;

@Repository
public interface RsvpRepository extends JpaRepository<Rsvp, String> {

}

