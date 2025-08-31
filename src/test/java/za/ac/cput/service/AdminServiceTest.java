package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Admin.AdminRole;
import za.ac.cput.factory.AdminFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    private static Admin admin;

    @BeforeAll
    static void setUp() {
        admin = AdminFactory.createAdmin(
                "k13/admin", "John", "Maputla", "0714567890", "maputla@gmail.com", AdminRole.ADMIN);
    }

    @Test
    @Order(1)
    void add() {
        Admin addAdmin = adminService.create(admin);
        assertNotNull(addAdmin.getId());
        assertEquals("John", addAdmin.getName());
        admin = addAdmin;
    }

    @Test
    @Order(2)
    void read() {
        Admin readAdmin = adminService.read(admin.getId());
        assertNotNull(readAdmin);
        assertEquals(admin.getEmail(), readAdmin.getEmail());
    }

    @Test
    @Order(3)
    void update() {
        Admin updateAdmin = new Admin.Builder()
                .copy(admin)
                .setName("Thomas")
                .build();
        Admin updated = adminService.update(updateAdmin);
        assertEquals("Thomas", updated.getName());
    }

    @Test
    @Order(5)
    void delete() {
        boolean success = adminService.delete(admin.getId());
        assertTrue(success);
        assertNull(adminService.read(admin.getId()));
    }

    @Test
    @Order(4)
    void getAll() {
        List<Admin> adminList = adminService.getAll();
        assertNotNull(adminList);
        //System.out.println("All Admins: " + adminList);
    }


}
