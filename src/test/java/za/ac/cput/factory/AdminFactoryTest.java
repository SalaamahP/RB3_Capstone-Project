/*AdminFactoryTest.java
AdminFactoryTest class
Author: OP Phologane (230690939)
Date: 18/05/2025 */
package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Admin.AdminRole;

import static org.junit.jupiter.api.Assertions.*;

public class AdminFactoryTest {
    // Test cases for AdminFactory
    // 1. Test creation of Admin with valid data
    // 2. Test creation of Admin with invalid data (e.g., null values)
    // 3. Test creation of Admin with invalid email format

    // Example test case
    @Test
    void testCreateAdmin() {
        // Create an Admin object using the factory
        Admin admin = AdminFactory.createAdmin("password123", "Mariana", "Stone", "0823459876", "stonema@gmail.com"
                , AdminRole.SYSTEM_ADMIN);
        // Verify that the Admin object is not null
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
    void testAdminWithNullValue() {
        Admin admin = AdminFactory.createAdmin("code1i239", "", "Boston", "0783746598", "boston@gmail.com", AdminRole.STAFF);
        assertNull(admin, "Admin should be null due to empty name");
    }

    @Test
    void testAdminWithInvalidEmail() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> AdminFactory.createAdmin("wbsjbhms78", "Tom", "Boston", "0780780456", "BOSTONgmail", AdminRole.STAFF)
        );
        assertEquals("Email is invalid", exception.getMessage(), "Exception message should match");
    }
}
