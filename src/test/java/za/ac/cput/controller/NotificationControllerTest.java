package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.ac.cput.domain.Notification;
import za.ac.cput.service.NotificationService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private NotificationService service;

    @Test
    void createAndGet() throws Exception {
        Notification n = new Notification.Builder()
                .setNotificationID("N400")
                .setMessage("Controller Test")
                .setStudentID("S400")
                .setEventID("E400")
                .build();

        // Mock save
        when(service.save(any(Notification.class))).thenReturn(n);

        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(n)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationID").value("N400"));

        // Mock read
        when(service.read("N400")).thenReturn(Optional.of(n));

        mockMvc.perform(get("/notifications/N400"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Controller Test"));
    }

    @Test
    void getAllNotifications() throws Exception {
        Notification n1 = new Notification.Builder()
                .setNotificationID("N401")
                .setMessage("Test 1")
                .setStudentID("S401")
                .setEventID("E401")
                .build();

        Notification n2 = new Notification.Builder()
                .setNotificationID("N402")
                .setMessage("Test 2")
                .setStudentID("S402")
                .setEventID("E402")
                .build();

        List<Notification> notifications = Arrays.asList(n1, n2);

        // Mock getAll
        when(service.getAll()).thenReturn(notifications);

        mockMvc.perform(get("/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].notificationID").value("N401"))
                .andExpect(jsonPath("$[1].notificationID").value("N402"));
    }

    @Test
    void deleteNotification() throws Exception {
        // Mock delete (no return needed)
        mockMvc.perform(delete("/notifications/N400"))
                .andExpect(status().isNoContent());
    }
}
