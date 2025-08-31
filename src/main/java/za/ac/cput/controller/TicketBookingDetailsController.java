//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.service.TicketBookingDetailsService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class TicketBookingDetailsController {

    private final TicketBookingDetailsService service;

    public TicketBookingDetailsController(TicketBookingDetailsService service) {
        this.service = service;
    }

    // Create
    @PostMapping
    public ResponseEntity<TicketBookingDetails> create(@RequestBody TicketBookingDetails booking) {
        return ResponseEntity.ok(service.save(booking));
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketBookingDetails> read(@PathVariable String id) {
        TicketBookingDetails booking = service.read(id);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Read all
    @GetMapping
    public List<TicketBookingDetails> getAll() {
        return service.getAll();
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

