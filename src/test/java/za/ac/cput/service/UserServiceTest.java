package za.ac.cput.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import za.ac.cput.domain.User;
import za.ac.cput.factory.UserFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserService service;
    private static User user;

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser("Mike", "Kim", "kim@gmail.com", "kimm123", "0895621400",null, "221301720");
    }

    @Test
    @Order(1)
    void add() {
        User addUser = service.create(user);
        assertNotNull(addUser.getUserId());
        assertEquals("Mike", addUser.getName());
    }

    @Test
    @Order(2)
    void read() {
        User read = service.read(user.getUserId());
        assertNotNull(read);
        assertEquals(user.getEmail(), read.getEmail());
    }

    @Test
    @Order(3)
    void update() {
        User updatedUser = new User.Builder()
                .copy(user)
                .setName("Jonathan")
                .build();
        User updated = service.update(updatedUser);
        assertEquals("Jonathan", updated.getName());
    }

    @Test
    @Order(4)
    void getAll() {
        List<User> users = service.getAll();
        assertNotNull(users);
        System.out.println("User List" + users);
    }

    @Test
    @Order(5)
    void delete() {
        boolean deleted = service.delete(user.getUserId());
        assertTrue(deleted);
        assertNull(service.read(user.getUserId()));
    }
}




