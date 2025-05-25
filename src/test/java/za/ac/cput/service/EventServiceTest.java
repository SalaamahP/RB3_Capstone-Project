package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.cput.domain.Event;
import za.ac.cput.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        event = new Event.Builder()
                .setEventId(Long.parseLong("event123"))
                .setEventName("Spring Boot Workshop")
                .setDateTime(LocalDateTime.parse("2025-05-18"))
                .build();
    }

    @Test
    void testCreate() {
        when(eventRepository.save(event)).thenReturn(event);

        Event createdEvent = eventService.create(event);
        assertNotNull(createdEvent);
        assertEquals("event123", createdEvent.getEventId());

        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testRead() {
        when(eventRepository.findById("event123")).thenReturn(Optional.of(event));

        Event foundEvent = eventService.read("event123");
        assertNotNull(foundEvent);
        assertEquals("event123", foundEvent.getEventId());

        verify(eventRepository, times(1)).findById("event123");
    }

    @Test
    void testUpdate() {
        when(eventRepository.existsById("event123")).thenReturn(true);
        when(eventRepository.save(event)).thenReturn(event);

        Event updatedEvent = eventService.update(event);
        assertNotNull(updatedEvent);
        assertEquals("event123", updatedEvent.getEventId());

        verify(eventRepository, times(1)).existsById("event123");
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testDelete() {
        when(eventRepository.existsById("event123")).thenReturn(true);

        boolean deleted = eventService.delete("event123");
        assertTrue(deleted);

        verify(eventRepository, times(1)).existsById("event123");
        verify(eventRepository, times(1)).deleteById("event123");
    }
}