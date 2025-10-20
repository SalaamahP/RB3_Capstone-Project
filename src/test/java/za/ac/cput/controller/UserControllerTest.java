package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Venue;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.factory.VenueFactory;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    private static User user;


    @Autowired
    TestRestTemplate restTemplate;


    @LocalServerPort
    private int port;

    private  String BASE_URL; //= 8080/SEMS/student";

    @BeforeEach
    void init() {
        BASE_URL = "http://localhost:" + port + "/SEMS/user";
    }

    @BeforeAll
    public static void setUp() {
        user = UserFactory.createUser("Alice", "Thymefield", "alice@gmail.com", "password123", "0832457221", "230781420", null);

    }

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<User> postResponse = restTemplate.postForEntity(url,user, User.class);
        assertNotNull(postResponse.getBody());

        user = postResponse.getBody();
        assertEquals("Alice",user.getName());
        System.out.println("Created: " + user.getName());

    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + user.getUserId();
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
        assertEquals(user.getUserId(), Objects.requireNonNull(response.getBody()).getUserId());
        System.out.println("Read user: " + user );}

    @Test
    @Order(3)
    void update() {
        User updatedUser = new User.Builder().copy(user)
                .setName("Amy")
                .build();

       // restTemplate.put(BASE_URL + "/update/" + user.getUserId(), updatedUser);
        restTemplate.put(BASE_URL + "/update", updatedUser);
        ResponseEntity<User> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + user.getUserId(), User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedUser.getName(), response.getBody().getName());
        System.out.println("updated user: " + response.getBody());

    }

    @Test
    @Order(5)
    void delete() {
        String url = BASE_URL + "/delete/" + user.getUserId();

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
        System.out.println("Deleted user: " + user.getUserId() );

        ResponseEntity<User>response = this.restTemplate.getForEntity(BASE_URL + "/read/" + user.getUserId(), User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(4)
    void getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<User[]> response = this.restTemplate.getForEntity(url, User[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All:" );
        for (User user : response.getBody()) {
            System.out.println(user);
        }
    }
}

