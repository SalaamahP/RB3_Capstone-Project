/*AdminFactoryTest.java
AdminFactoryTest class
Author: OP Phologane (230690939)
Date: 18/05/2025 */
package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.Admin;
import za.ac.cput.util.Helper;
import za.ac.cput.domain.Admin.AdminRole;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminFactoryTest {

    @Test
    @Order(1)
    void testCreateAdmin() {
        // Create an Admin object using the factory
        Admin admin = AdminFactory.createAdmin("password123", "Mariana", "Stone", "0823459876", "stonema@gmail.com"
                , AdminRole.SYSTEM_ADMIN);

        assertNotNull(admin, "Admin should not be null");
        // Verify that the Admin object has the expected values
        assertEquals("password123", admin.getPassword(), "Password should match");
        assertEquals("Mariana", admin.getName(), "Name should match");
        assertEquals("Stone", admin.getSurname(), "Surname should match");
        assertEquals("0823459876", admin.getPhone(), "Phone should match");
        assertEquals("stonema@gmail.com", admin.getEmail(), "Email should match");
        assertEquals(AdminRole.SYSTEM_ADMIN, admin.getAdminRole(), "Admin role should match");

    }

    @Test
    void testCreateAdmin_Fail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Admin admin = AdminFactory.createAdmin(null, "Mariana", "Stone", "0823459876", "stonema@gmail.com", AdminRole.SYSTEM_ADMIN);
            assertNull(admin);
        });
        assertFalse(exception.getMessage().contains("Password is invalid"));
    }

    @Test
    void isValidEmail() {
        assertTrue(Helper.isValidEmail("mariah@mycput.ac.za"));
    }

    @Test
    void isValidPhone() {
        assertTrue(Helper.isValidPhone("0823459876"));
    }
    @Test
    void createAdmin_invalidPhone() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Admin admin = AdminFactory.createAdmin("Mari123", "Mariana", "Stone", "0823459", "stonema@gmail.com", AdminRole.SYSTEM_ADMIN);
        });
        assertEquals("Phone number is invalid", exception.getMessage());
    }

    @Test
    void createAdmin_invalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Admin admin = AdminFactory.createAdmin("Mari123", "Mariana", "Stone", "0823459876", "stonemagmail.com", AdminRole.SYSTEM_ADMIN);
        });
        assertEquals("Email is invalid", exception.getMessage());
    }
}
