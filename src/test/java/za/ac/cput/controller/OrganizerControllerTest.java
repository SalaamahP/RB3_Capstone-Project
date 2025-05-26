/* OrganizerControllerTest.java
* OrganizerControllerTest class
* Author: Oratile Phologane (230690939)
* Date: 25 May 2025
*/

package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.ac.cput.domain.Organizer;
import za.ac.cput.service.OrganizerService;
import za.ac.cput.domain.Organizer.OrganizerType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(OrganizerController.class)
public class OrganizerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final OrganizerService organizerService = mock(OrganizerService.class);

    @Autowired
    private ObjectMapper objectMapper;

    private Organizer organizer;

    @BeforeEach
    void setUp() {
        organizer = new Organizer.Builder()
                .setName("Poko")
                .setSurname("Moeti")
                .setPhone("0786523945")
                .setEmail("poko@gmail.com")
                .setOrganizerType(OrganizerType.CORPORATION)
                .build();
    }

    @Test
    void testCreateOrganizer() throws Exception {
        when(organizerService.create(any(Organizer.class))).thenReturn(organizer);

        mockMvc.perform(post("/api/organizer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(organizer.getName()));

    }

    @Test
    void testReadOrganizer() throws Exception {
        when(organizerService.read(eq(organizer.getId()))).thenReturn(organizer);

        mockMvc.perform(get("/api/organizer/{id}", organizer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(organizer.getName()))
                .andExpect(jsonPath("$.surname").value(organizer.getSurname()))
                .andExpect(jsonPath("$.phone").value(organizer.getPhone()))
                .andExpect(jsonPath("$.email").value(organizer.getEmail()))
                .andExpect(jsonPath("$.organizerType").value(organizer.getOrganizerType().toString()));
    }

    @Test
    void testUpdateOrganizer() throws Exception {
        when(organizerService.update(any(Organizer.class))).thenReturn(organizer);

        mockMvc.perform(put("/api/organizer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(organizer.getName()));
    }

    @Test
    void testDeleteOrganizer() throws Exception {
        doNothing().when(organizerService).delete(eq(organizer.getId()));

        mockMvc.perform(delete("/api/organizer/{id}", organizer.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllOrganizers() throws Exception {
        List<Organizer> organizers = List.of(organizer);
        when(organizerService.getAll()).thenReturn(organizers);

        mockMvc.perform(get("/api/organizer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(organizer.getName()))
                .andExpect(jsonPath("$[0].surname").value(organizer.getSurname()))
                .andExpect(jsonPath("$[0].phone").value(organizer.getPhone()))
                .andExpect(jsonPath("$[0].email").value(organizer.getEmail()))
                .andExpect(jsonPath("$[0].organizerType").value(organizer.getOrganizerType().toString()));
    }
}
