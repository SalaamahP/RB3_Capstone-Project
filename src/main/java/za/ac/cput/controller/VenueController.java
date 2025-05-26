package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.Venue;
import za.ac.cput.service.VenueService;

import java.util.List;

@RestController
@RequestMapping("/venue")
public class VenueController {
    private VenueService service;

    @Autowired VenueController(VenueService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Venue create(@RequestBody Venue venue) {
        return service.create(venue);

    }

    @GetMapping("/read/{id}")
    public Venue read(@PathVariable Long id) {
        return service.read(id);
    }

    @PutMapping("/update")
    public Venue update(@RequestBody Venue venue) {
        return service.update(venue);
    }

    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/getall")
    public List<Venue> getAll() {
        return service.getAll();
    }
}
