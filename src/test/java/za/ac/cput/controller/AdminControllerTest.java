/* AdminControllerTest.java
    * AdminControllerTest class
    * Author: Oratile Phologane (230690969)
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
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Admin.AdminRole;
import za.ac.cput.repository.AdminRepository;
import za.ac.cput.service.AdminService;



import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final AdminService adminService = mock(AdminService.class);

    @Autowired
    private ObjectMapper objectMapper;

    private Admin admin;
    @Autowired
    private AdminRepository adminRepository;

    @BeforeEach
    public void setUp() {
        admin = new Admin.Builder()
                .setName("Mia")
                .setSurname("Kings")
                .setPhone("0809654127")
                .setEmail("kings@gmail.com")
                .setPassword("password123")
                .setAdminRole(AdminRole.SYSTEM_ADMIN)
                .build();

    }

    @Test
    void testSaveAdmin() throws Exception {
        when(adminService.create(any(Admin.class))).thenReturn(admin);

        mockMvc.perform(post("/api/admin/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(admin.getName()))
                .andExpect(jsonPath("$.surname").value(admin.getSurname()))
                .andExpect(jsonPath("$.phone").value(admin.getPhone()))
                .andExpect(jsonPath("$.email").value(admin.getEmail()))
                .andExpect(jsonPath("$.adminRole").value(admin.getAdminRole().toString()));
    }

    @Test
    void testCreateAdmin() throws Exception {
        when(adminService.create(any(Admin.class))).thenReturn(admin);

        mockMvc.perform(post("/api/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.AdminId").value(admin.getId()));
    }

    @Test
    void testReadAdmin() throws Exception {
        when(adminService.read(eq(admin.getId()))).thenReturn(admin);

        mockMvc.perform(get("/api/admin/read/" + admin.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(admin.getName()))
                .andExpect(jsonPath("$.surname").value(admin.getSurname()))
                .andExpect(jsonPath("$.phone").value(admin.getPhone()))
                .andExpect(jsonPath("$.email").value(admin.getEmail()))
                .andExpect(jsonPath("$.adminRole").value(admin.getAdminRole().toString()));

    }

    @Test
    void testUpdateAdmin() throws Exception {
        when(adminService.update(any(Admin.class))).thenReturn(admin);

        mockMvc.perform(put("/api/admin/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.AdminId").value(admin.getId()));
    }

    @Test
    void testDeleteAdmin() throws Exception {
        doNothing().when(adminService).delete(eq(admin.getId()));

        mockMvc.perform(delete("/api/admin/delete/" + admin.getId()))
                .andExpect(status().isNoContent());

        verify(adminService, times(1)).delete(eq(admin.getId()));
    }

    @Test
    void testGetAllAdmins() throws Exception {
        List<Admin> admins = List.of(admin);
        when(adminService.getAll()).thenReturn(admins);

        mockMvc.perform(get("/api/admin/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(admin.getName()))
                .andExpect(jsonPath("$[0].surname").value(admin.getSurname()))
                .andExpect(jsonPath("$[0].phone").value(admin.getPhone()))
                .andExpect(jsonPath("$[0].email").value(admin.getEmail()))
                .andExpect(jsonPath("$[0].adminRole").value(admin.getAdminRole().toString()));
    }

}
