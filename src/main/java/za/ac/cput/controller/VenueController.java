package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
//import za.ac.cput.domain.Student;
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
    public Venue update( @RequestBody Venue venue) {
        Venue existingVenue = service.read(venue.getVenueId());
        if (existingVenue == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found" + venue.getVenueId());
        }

        Venue updatedVenue = new Venue.Builder()
                .copy(existingVenue)
                .setVenueName(venue.getVenueName())
                .setVenueLocation(venue.getVenueLocation())
                .setCapacity(venue.getCapacity())
                .build();
        return service.update(updatedVenue);
    }

    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/getAll")
    public List<Venue> getAll() {
        return service.getAll();
    }
}
