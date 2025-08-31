package za.ac.cput.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Organizer;

import java.util.List;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
//List<Organizer> findByOrganizerId(Long OrganizerId);
}
