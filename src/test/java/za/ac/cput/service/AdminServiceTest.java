package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Admin.AdminRole;
import za.ac.cput.repository.AdminRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServiceImpl adminService;
    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = new Admin.Builder()
                .setName("Thomas")
                .setSurname("Koe")
                .setPhone("0877752601")
                .setEmail("koe@gmail.com")
                .setPassword("k12Admin77")
                .setAdminRole(AdminRole.SYSTEM_ADMIN)
                .build();
        adminRepository.save(admin);
        System.out.println(admin.getId());
    }

    @Test
    void testCreateAdmin() {
        when(adminRepository.save(any(Admin.class))).thenAnswer(invocation -> {
            Admin savedAdmin = invocation.getArgument(0);
            savedAdmin = new Admin.Builder().copy(savedAdmin).id(1L).build(); // Simulating the ID
            return savedAdmin;
        });

        Admin createdAdmin = adminService.create(admin);
        assertNotNull(createdAdmin);
        assertEquals(AdminRole.SYSTEM_ADMIN, createdAdmin.getAdminRole());
    }
    @Test
    void testReadAdmin() {
        when(adminRepository.findById(anyLong())).thenReturn(Optional.of(admin));
        Admin foundAdmin = adminService.read(1L);
        assertNotNull(foundAdmin);
        assertEquals(AdminRole.SYSTEM_ADMIN, foundAdmin.getAdminRole());
    }

    @Test
    void testUpdateAdminRole() {
        Admin updatedAdmin = new Admin.Builder()
                .copy(admin)
                .setAdminRole(AdminRole.STAFF) // Change role
                .build();

        when(adminRepository.existsById(anyLong())).thenReturn(true);
        when(adminRepository.save(any(Admin.class))).thenReturn(updatedAdmin);

        Admin result = adminService.update(updatedAdmin);
        assertNotNull(result);
        assertEquals(AdminRole.STAFF, result.getAdminRole()); // Ensure role update
    }

    @Test
    void testDeleteAdmin() {
        when(adminRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(adminRepository).deleteById(anyLong());

        boolean isDeleted = adminService.delete(1L);
        assertEquals(true, isDeleted);
        verify(adminRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllAdmins() {
        when(adminRepository.findAll()).thenReturn(List.of(admin));

        List<Admin> admins = adminService.getAll();
        assertNotNull(admins);
        assertEquals(1, admins.size());
    }

}
