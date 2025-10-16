//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.TicketBookingDetails;

@Repository
public interface TicketBookingDetailsRepository extends JpaRepository<TicketBookingDetails, Long> {
}
