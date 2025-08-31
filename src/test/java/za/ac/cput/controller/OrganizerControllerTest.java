/* OrganizerControllerTest.java
* OrganizerControllerTest class
* Author: Oratile Phologane (230690939)
* Date: 25 May 2025
*/

package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Organizer;
import za.ac.cput.factory.OrganizerFactory;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganizerControllerTest {
    private static Organizer organizer;

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    private String BASE_URL; //= 8080/SEMS/organizer";

    @BeforeEach
    void init() {
        BASE_URL = "http://localhost:" + port + "/SEMS/organizer";
    }

    @BeforeAll
    public static void setUp() {
        organizer = OrganizerFactory.createOrganizer("xV9800", "Mark", "Colleson", "0824567890", "colemark@gmail.com", Organizer.OrganizerType.FACULTY);
    }

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Organizer> postResponse = restTemplate.postForEntity(url, organizer, Organizer.class);
        assertNotNull(postResponse.getBody());

        organizer = postResponse.getBody();
        assertEquals("Mark", organizer.getName());
        System.out.println("Created: " + organizer.getName());

    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + organizer.getId();
        ResponseEntity<Organizer> response = restTemplate.getForEntity(url, Organizer.class);
        assertEquals(organizer.getId(), Objects.requireNonNull(response.getBody()).getId());
        System.out.println("Read: " + organizer.getId());
    }

    @Test
    @Order(3)
    void update() {
        assertNotNull(organizer.getId());
        Organizer updatedOrganizer = new Organizer.Builder().copy(organizer).setSurname("Coleman").build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedOrganizer);

        ResponseEntity<Organizer> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + organizer.getId(), Organizer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedOrganizer.getSurname(), response.getBody().getSurname());
        System.out.println("Updated Organizer Surname: " + response.getBody().getSurname());

        organizer = response.getBody();
    }

    @Test
    @Order(5)
    void delete() {
        String url = BASE_URL + "/delete/" + organizer.getId();
        this.restTemplate.delete(url);

        ResponseEntity<Organizer> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + organizer.getId(), Organizer.class);
        assertNull(response.getBody());
        System.out.println("Deleted Organizer with ID: " + organizer.getId());
    }

    @Test
    @Order(4)
    void getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Organizer[]> response = restTemplate.getForEntity(url, Organizer[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All: ");
        for (Organizer organizer : response.getBody()) {
            System.out.println(organizer);
        }
    }
}
