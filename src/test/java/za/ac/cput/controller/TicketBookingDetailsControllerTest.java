package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.ac.cput.domain.TicketBookingDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TicketBookingDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createAndGetBooking() throws Exception {
        TicketBookingDetails booking = new TicketBookingDetails.Builder()
                .setBookingID("B500")
                .setStudentId("S500")
                .setEventId("E500")
                .setNumberOfTickets(3)
                .build();

        // Create booking
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingID").value("B500"));

        // Read booking
        mockMvc.perform(get("/bookings/B500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value("S500"))
                .andExpect(jsonPath("$.eventId").value("E500"))
                .andExpect(jsonPath("$.numberOfTickets").value(3));
    }

    @Test
    void getAllBookings() throws Exception {
        TicketBookingDetails booking1 = new TicketBookingDetails.Builder()
                .setBookingID("B501")
                .setStudentId("S501")
                .setEventId("E501")
                .setNumberOfTickets(1)
                .build();

        TicketBookingDetails booking2 = new TicketBookingDetails.Builder()
                .setBookingID("B502")
                .setStudentId("S502")
                .setEventId("E502")
                .setNumberOfTickets(2)
                .build();

        // Save them first
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(booking1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(booking2)))
                .andExpect(status().isOk());

        // Get all bookings
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deleteBooking() throws Exception {
        TicketBookingDetails booking = new TicketBookingDetails.Builder()
                .setBookingID("B503")
                .setStudentId("S503")
                .setEventId("E503")
                .setNumberOfTickets(1)
                .build();

        // Save first
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(booking)))
                .andExpect(status().isOk());

        // Delete booking
        mockMvc.perform(delete("/bookings/B503"))
                .andExpect(status().isNoContent());

        // Ensure it no longer exists
        mockMvc.perform(get("/bookings/B503"))
                .andExpect(status().isNotFound());
    }
}
