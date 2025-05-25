//[author] Jaedon Prince, 230473474
//[date] 25/05/2025

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
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.service.TicketBookingDetailsService;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketBookingDetailsController.class)
public class TicketBookingDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketBookingDetailsService service;

    private TicketBookingDetails booking;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        booking = new TicketBookingDetails.Builder()
                .setUserId(1L)
                .setEventId("EV001")
                .setQuantity(2)
                .setTotal(300.00)
                .setPaymentSelection(TicketBookingDetails.PaymentOption.CASH)
                .setStatus(TicketBookingDetails.Status.PENDING)
                .setDateBooked(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateBooking() throws Exception {
        when(service.create(any())).thenReturn(booking);

        mockMvc.perform(post("/api/ticket-bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value("EV001"));
    }

    @Test
    void testReadBooking() throws Exception {
        when(service.read(any())).thenReturn(booking);

        mockMvc.perform(get("/api/ticket-bookings/" + booking.getBookingId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    void testGetAllBookings() throws Exception {
        when(service.getAll()).thenReturn(Collections.singletonList(booking));

        mockMvc.perform(get("/api/ticket-bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testDeleteBooking() throws Exception {
        when(service.delete(booking.getBookingId())).thenReturn(true);

        mockMvc.perform(delete("/api/ticket-bookings/" + booking.getBookingId()))
                .andExpect(status().isOk());
    }
}
