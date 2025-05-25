//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.service.TicketBookingDetailsService;

import java.util.List;

@RestController
@RequestMapping("/api/ticket-bookings")
public class TicketBookingDetailsController {

    private final TicketBookingDetailsService service;

    public TicketBookingDetailsController(TicketBookingDetailsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TicketBookingDetails> create(@RequestBody TicketBookingDetails details) {
        return ResponseEntity.ok(service.create(details));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketBookingDetails> read(@PathVariable String id) {
        TicketBookingDetails details = service.read(id);
        return details != null ? ResponseEntity.ok(details) : ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<TicketBookingDetails> update(@RequestBody TicketBookingDetails details) {
        return ResponseEntity.ok(service.update(details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TicketBookingDetails>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
