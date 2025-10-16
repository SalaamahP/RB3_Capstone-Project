/* Event Controller.java
 * Event Controller class
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 25 May 2025
 */
package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import za.ac.cput.domain.Event;
import za.ac.cput.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Event create(@RequestBody Event event) {
        return service.create(event);
    }

    @GetMapping("/read/{id}")
    public Event read(@PathVariable Long id) {
        Event existingEvent = service.read(id);
        if (existingEvent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found: " + id);
        }
        return existingEvent;
    }

    @PutMapping("/update")
    public Event update(@RequestBody Event event) {
        Event existingEvent = service.read(event.getEventId());
        if (existingEvent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found: " + event.getEventId());
        }

        Event updatedEvent = new Event.Builder()
                .copy(existingEvent)
                .setEventName(event.getEventName())
                .setEventDescription(event.getEventDescription())
                .setEventCategory(event.getEventCategory())
                .setDateTime(event.getDateTime())
                .setVenueId(event.getVenueId())
                .setUserId(event.getUserId())
                .setTicketPrice(event.getTicketPrice())
                .build();

        return service.update(updatedEvent);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/getAll")
    public List<Event> getAll() {
        return service.getAll();
    }
}
