//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.TicketBookingDetails;

public interface TicketBookingDetailsRepository extends JpaRepository<TicketBookingDetails, String> {

}
