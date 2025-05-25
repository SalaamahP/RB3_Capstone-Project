package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Event;
import za.ac.cput.repository.EventRepository;
import za.ac.cput.service.EventService;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event read(String id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.orElse(null);
    }

    @Override
    public Event update(Event event) {
        if (eventRepository.existsById(String.valueOf(event.getEventId()))) {
            return eventRepository.save(event);
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}