package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
}
