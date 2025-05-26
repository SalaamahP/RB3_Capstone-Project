package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Organizer;
import za.ac.cput.service.OrganizerService;

import java.util.List;

@RestController
@RequestMapping("/api/organizer")
public class OrganizerController {
    private final OrganizerService organizerService;

    @Autowired
    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    // Endpoint to create a new organizer
    @PostMapping("/create")
    public ResponseEntity<Organizer> createOrganizer(@RequestBody Organizer organizer) {
        Organizer createdOrganizer = organizerService.create(organizer);
        return ResponseEntity.ok(createdOrganizer);
    }

    // Endpoint to read an organizer by ID
    @GetMapping("/read/{id}")
    public ResponseEntity<Organizer> readOrganizer(@PathVariable Long id) {
        Organizer organizer = organizerService.read(id);
        return ResponseEntity.ok(organizer);
    }

    // Endpoint to update an existing organizer
    @PutMapping("/update")
    public ResponseEntity<Organizer> updateOrganizer(@RequestBody Organizer organizer) {
        Organizer updatedOrganizer = organizerService.update(organizer);
        return ResponseEntity.ok(updatedOrganizer);
    }

    // Endpoint to delete an organizer by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrganizer(@PathVariable Long id) {
        boolean deleted = organizerService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Endpoint to get all organizers
    @GetMapping("/getall")
    public ResponseEntity<List<Organizer>> getAllOrganizers() {
        List<Organizer> organizers = organizerService.getAll();
        return ResponseEntity.ok(organizers);
    }
}
