package za.ac.cput.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import za.ac.cput.domain.Role;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    @Order(1)
    void testFindStudentRole() {
        Role role = roleService.findByRoleName("STUDENT");
        assertNotNull(role);
        assertEquals("STUDENT", role.getRoleName());
    }

    @Test
    @Order(2)
    void testFindAdminRole() {
        Role role = roleService.findByRoleName("ADMIN");
        assertNotNull(role);
        assertEquals("ADMIN", role.getRoleName());
    }

    @Test
    @Order(3)
    void testFindOrganizerRole() {
        Role role = roleService.findByRoleName("ORGANIZER");
        assertNotNull(role);
        assertEquals("ORGANIZER", role.getRoleName());
    }
}