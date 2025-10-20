package za.ac.cput.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserFactoryTest {


        @Test
        @Order(1)
        void testCreateUser() {
            User user = UserFactory.createUser("Kevin","Malone","malone@gmail.com","kev123", "0820505336", null, "221301720");

            assertNotNull(user, "User should not be null");

            assertEquals("Kevin", user.getName(), "Names should match");
            assertEquals("Malone", user.getSurname(), "Surnames should match");
            assertEquals("kev123", user.getPassword(), "Passwords should match");
            assertEquals("0820505336", user.getPhone(), "Phone numbers should match");

        }

        //    @Test
//    void testCreateRole() {
//        Role role = new Role("ADMIN", "The administrator role with full access");
//        assertNotNull(role, "Role should not be null");
//        assertEquals("ADMIN", role.getRoleName(), "Role names should match");
//    }
        @Test
        void testCreateUser_Fail() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                User  user = UserFactory.createUser(null,"Malone","malone@gmail.com","kev123", "0820505336",null, "221301720");
                assertNull(user);
            });
            assertFalse(exception.getMessage().contains("User name is required"));
        }

        @Test
        void isValidEmail() {
            assertTrue(Helper.isValidEmail("malone@myucput.ac.za"));

        }

        @Test
        void createUser_invalidEmail() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                User user = UserFactory.createUser(null,"Malone","malonegmail.com","kev123", "0820505336",null, "221301720");
            });
            assertTrue(exception.getMessage().contains("Email is invalid"));

        }


    }

