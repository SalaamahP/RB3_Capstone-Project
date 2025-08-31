package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Enum.Status;
import za.ac.cput.domain.Rsvp;
import za.ac.cput.service.RsvpService;

import java.util.List;

@RestController
@RequestMapping("/api/rsvp")
public class RsvpController {

    private final RsvpService rsvpService;

    @Autowired
    public RsvpController(RsvpService rsvpService) {
        this.rsvpService = rsvpService;
    }

    // Helper method to validate status
    private boolean isValidStatus(String status) {
        try {
           Status.valueOf(status);  // Try to convert to the Status enum
            return true;  // Valid status
        } catch (IllegalArgumentException e) {
            return false;  // Invalid status
        }
    }

    // Create a new RSVP
    @PostMapping
    public ResponseEntity<Rsvp> create(@RequestBody Rsvp rsvp) {
        if (!isValidStatus(rsvp.getStatus().name())) {
            return ResponseEntity.badRequest().body(null);  // Return 400 if status is invalid
        }
        return ResponseEntity.ok(rsvpService.create(rsvp));
    }

    // Read RSVP by ID
    @GetMapping("/{id}")
    public ResponseEntity<Rsvp> read(@PathVariable String id) {
        Rsvp rsvp = rsvpService.read(id);
        return rsvp != null ? ResponseEntity.ok(rsvp) : ResponseEntity.notFound().build();
    }

    // Update an existing RSVP
//    @PutMapping("/{id}")
//    public ResponseEntity<Rsvp> update(@PathVariable String id, @RequestBody Rsvp rsvp) {
//        if (!isValidStatus(rsvp.getStatus().name())) {
//            return ResponseEntity.badRequest().body(null);  // Return 400 if status is invalid
//        }
//      //  Rsvp updated = rsvpService.update(id, rsvp);
//     //   return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
//    }

    // Delete an RSVP
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable String id) {
//        boolean deleted = rsvpService.delete(id);
//        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
//    }

    // Get all RSVPs
    @GetMapping
    public ResponseEntity<List<Rsvp>> getAll() {
        return ResponseEntity.ok(rsvpService.getAll());
    }
}
