
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

import za.ac.cput.domain.Rsvp;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.Event;
import za.ac.cput.service.RsvpService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RsvpController.class)
public class RsvpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RsvpService rsvpService;

    @Autowired
    private ObjectMapper objectMapper;

    private Rsvp rsvp;

    @BeforeEach
    void setup() {
        Student student = new Student.Builder()
                .setId("student1")
                .setName("Test Student")
                .build();

        Event event = new Event.Builder()
                .setId("event1")
                .setName("Test Event")
                .build();

        rsvp = new Rsvp.Builder()
                .setRsvpID("rsvp1")
                .setStudent(student)
                .setEvent(event)
                .setStatus(Rsvp.Status.CONFIRMED)
                .build();
    }

    @Test
    void testGetAll() throws Exception {
        Mockito.when(rsvpService.findAll()).thenReturn(Arrays.asList(rsvp));

        mockMvc.perform(get("/api/rsvps"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rsvpID").value(rsvp.getRsvpID()));
    }

    @Test
    void testGetById_found() throws Exception {
        Mockito.when(rsvpService.findById("rsvp1")).thenReturn(Optional.of(rsvp));

        mockMvc.perform(get("/api/rsvps/rsvp1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rsvpID").value(rsvp.getRsvpID()));
    }

    @Test
    void testGetById_notFound() throws Exception {
        Mockito.when(rsvpService.findById("rsvp2")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/rsvps/rsvp2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        Mockito.when(rsvpService.save(any(Rsvp.class))).thenReturn(rsvp);

        mockMvc.perform(post("/api/rsvps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rsvp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rsvpID").value(rsvp.getRsvpID()));
    }

    @Test
    void testUpdate_found() throws Exception {
        Mockito.when(rsvpService.update(eq("rsvp1"), any(Rsvp.class))).thenReturn(Optional.of(rsvp));

        mockMvc.perform(put("/api/rsvps/rsvp1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rsvp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rsvpID").value(rsvp.getRsvpID()));
    }

    @Test
    void testUpdate_notFound() throws Exception {
        Mockito.when(rsvpService.update(eq("rsvp2"), any(Rsvp.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/rsvps/rsvp2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rsvp)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_found() throws Exception {
        Mockito.when(rsvpService.deleteById("rsvp1")).thenReturn(true);

        mockMvc.perform(delete("/api/rsvps/rsvp1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_notFound() throws Exception {
        Mockito.when(rsvpService.deleteById("rsvp2")).thenReturn(false);

        mockMvc.perform(delete("/api/rsvps/rsvp2"))
                .andExpect(status().isNotFound());
    }
}
