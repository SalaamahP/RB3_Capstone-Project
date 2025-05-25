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
import za.ac.cput.domain.Notification;
import za.ac.cput.service.NotificationService;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService service;

    private Notification notification;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        notification = new Notification.Builder()
                .setNotificationID("NOTIF001")
                .setMessage("You have a new event reminder!")
                .setStudentID("STU123")
                .setEventID("EV456")
                .setTimestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateNotification() throws Exception {
        when(service.create(any())).thenReturn(notification);

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(notification)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationID").value("NOTIF001"));
    }

    @Test
    void testReadNotification() throws Exception {
        when(service.read("NOTIF001")).thenReturn(notification);

        mockMvc.perform(get("/api/notifications/NOTIF001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentID").value("STU123"));
    }

    @Test
    void testGetAllNotifications() throws Exception {
        when(service.getAll()).thenReturn(Collections.singletonList(notification));

        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testDeleteNotification() throws Exception {
        when(service.delete("NOTIF001")).thenReturn(true);

        mockMvc.perform(delete("/api/notifications/NOTIF001"))
                .andExpect(status().isOk());
    }
}
