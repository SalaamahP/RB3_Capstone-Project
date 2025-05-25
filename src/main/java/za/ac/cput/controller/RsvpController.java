package za.ac.cput.controller;

import za.ac.cput.domain.Rsvp;
import za.ac.cput.service.RsvpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rsvps")
public class RsvpController {

    private final RsvpService rsvpService;

    @Autowired
    public RsvpController(RsvpService rsvpService) {
        this.rsvpService = rsvpService;
    }

    @GetMapping
    public ResponseEntity<List<Rsvp>> getAll() {
        List<Rsvp> rsvps = rsvpService.findAll();
        return ResponseEntity.ok(rsvps);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rsvp> getById(@PathVariable String id) {
        Optional<Rsvp> rsvp = rsvpService.findById(id);
        return rsvp.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rsvp> create(@RequestBody Rsvp rsvp) {
        Rsvp created = rsvpService.save(rsvp);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rsvp> update(@PathVariable String id, @RequestBody Rsvp rsvp) {
        Optional<Rsvp> updated = rsvpService.update(id, rsvp);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = rsvpService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
