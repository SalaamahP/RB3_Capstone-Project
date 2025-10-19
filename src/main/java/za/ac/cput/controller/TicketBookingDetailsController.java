//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.service.TicketBookingDetailsService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class TicketBookingDetailsController {

    private final TicketBookingDetailsService service;

    @Autowired
    public TicketBookingDetailsController(TicketBookingDetailsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TicketBookingDetails> create(@RequestBody TicketBookingDetails booking) {
        TicketBookingDetails created = service.create(booking);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketBookingDetails> read(@PathVariable Long id) {
        TicketBookingDetails booking = service.read(id);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<TicketBookingDetails> update(@RequestBody TicketBookingDetails booking) {
        TicketBookingDetails updated = service.update(booking);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TicketBookingDetails>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}

