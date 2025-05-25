package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.ac.cput.domain.Event;
import za.ac.cput.service.EventService;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event.Builder()
                .setEventId(Long.parseLong("event123"))
                .setEventName("Spring Boot Workshop")
                .setDateTime(LocalDateTime.parse("2025-05-18"))
                .build();
    }

    @Test
    void testCreateEvent() throws Exception {
        Mockito.when(eventService.create(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/api/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(event.getEventId()));
    }

    @Test
    void testReadEvent() throws Exception {
        Mockito.when(eventService.read("event123")).thenReturn(event);

        mockMvc.perform(get("/api/event/event123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(event.getEventId()));
    }

    @Test
    void testUpdateEvent() throws Exception {
        Mockito.when(eventService.update(any(Event.class))).thenReturn(event);

        mockMvc.perform(put("/api/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(event.getEventId()));
    }

    @Test
    void testDeleteEvent() throws Exception {
        Mockito.when(eventService.delete("event123")).thenReturn(true);

        mockMvc.perform(delete("/api/event/event123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllEvents() throws Exception {
        Mockito.when(eventService.getAll()).thenReturn(Arrays.asList(event));

        mockMvc.perform(get("/api/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventId").value(event.getEventId()));
    }
}