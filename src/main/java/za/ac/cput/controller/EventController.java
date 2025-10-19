/* Event Controller.java
 * Event Controller class
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 25 May 2025
 */
package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Event;
import za.ac.cput.service.EventService;
import za.ac.cput.service.IEventService;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final IEventService IEventService;

    @Autowired
    public EventController(IEventService IEventService) {
        this.IEventService = IEventService;
    }

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        return ResponseEntity.ok(IEventService.create(event));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> read(@PathVariable long id) {
        Event event = IEventService.read(id);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<Event> update(@RequestBody Event event) {
        Event updated = IEventService.update(event);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean deleted = IEventService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAll() {
        return ResponseEntity.ok(IEventService.getAll());
    }
}
