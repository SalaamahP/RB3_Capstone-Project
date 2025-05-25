//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.service;

import za.ac.cput.domain.TicketBookingDetails;
import java.util.List;

public interface TicketBookingDetailsService {
    TicketBookingDetails create(TicketBookingDetails details);
    TicketBookingDetails read(String bookingId);
    TicketBookingDetails update(TicketBookingDetails details);
    boolean delete(String bookingId);
    List<TicketBookingDetails> getAll();
}