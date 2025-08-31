package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Venue;
import za.ac.cput.factory.VenueFactory;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VenueControllerTest {

    private static Venue venue;


    @Autowired
    TestRestTemplate restTemplate;


    @LocalServerPort
    private int port;

    private  String BASE_URL; //= 8080/SEMS/student";

    @BeforeEach
    void init() {
        BASE_URL = "http://localhost:" + port + "/SEMS/venue";
    }

    @BeforeAll
    public static void setUp() {
        venue = VenueFactory.createVenue("Commerce Building", "District Six Campus", 50);

    }

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Venue> postResponse = restTemplate.postForEntity(url, venue, Venue.class);
        assertNotNull(postResponse.getBody());

        venue = postResponse.getBody();
        assertEquals("Commerce Building",venue.getVenueName());
        System.out.println("Created: " + venue.getVenueName());

    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + venue.getVenueId();
        ResponseEntity<Venue> response = restTemplate.getForEntity(url, Venue.class);
        assertEquals(venue.getVenueId(), Objects.requireNonNull(response.getBody()).getVenueId());
        System.out.println("Read: " + venue.getVenueId());
    }

    @Test
    @Order(3)
    void update() {
        Venue updatedVenue = new Venue.Builder().copy(venue).setVenueName("Admin Building").build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedVenue);


        ResponseEntity<Venue> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + venue.getVenueId(), Venue.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedVenue.getVenueName(), response.getBody().getVenueName());
        System.out.println("updated venue: " + response.getBody());

    }

//    @Test
//    @Order(5)
//    void delete() {
//        String url = BASE_URL + "/delete/" + venue.getVenueId();
//        this.restTemplate.delete(url);
//
//
//        ResponseEntity<Venue> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + venue.getVenueId(), Venue.class);
//        assertNull(response.getBody());
//        System.out.println("Deleted: " + venue.getVenueId());
//
//    }

    @Test
    @Order(4)
    void getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Venue[]> response = this.restTemplate.getForEntity(url, Venue[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All:" );
        for (Venue venue : response.getBody()) {
            System.out.println(venue);
        }
    }
}