package za.ac.cput.controller;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Organizer;
import za.ac.cput.service.OrganizerService;

import java.util.List;

@RestController
@RequestMapping("/api/organizer")
@CrossOrigin(origins = "http://localhost:5173")
public class OrganizerController {
    private final OrganizerService organizerService;

    @Autowired
    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    // Endpoint to create a new organizer
    @PostMapping("/create")
    public Organizer create(@RequestBody Organizer organizer) {
        return organizerService.create(organizer);
    }

    // Endpoint to read an organizer by ID
    @GetMapping("/read/{id}")
    public Organizer read(@PathVariable Long id) {
        return organizerService.read(id);
    }

    // Endpoint to update an organizer
    @PutMapping("/update")
    public Organizer update(@RequestBody Organizer organizer) {
        return organizerService.update(organizer);
    }

    // Endpoint to delete an organizer by ID
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return organizerService.delete(id);
    }

    // Endpoint to get all organizers
    @GetMapping("/getall")
    public List<Organizer> getAll() {
        return organizerService.getAll();
    }
}
