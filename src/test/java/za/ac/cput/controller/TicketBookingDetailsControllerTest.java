//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.TicketBookingDetails;
import za.ac.cput.factory.TicketBookingDetailsFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketBookingDetailsControllerTest {

    private static TicketBookingDetails booking;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String BASE_URL;

    @BeforeEach
    void init() {
        BASE_URL = "http://localhost:" + port + "/SEMS/bookings";
    }

    @BeforeAll
    public static void setUp() {
        booking = TicketBookingDetailsFactory.createBooking(
                "STU001",
                "EVT001"
        );
    }

    @Test
    @Order(1)
    void testCreate() {
        ResponseEntity<TicketBookingDetails> postResponse = restTemplate.postForEntity(BASE_URL, booking, TicketBookingDetails.class);
        assertNotNull(postResponse.getBody());
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        booking = postResponse.getBody();
        assertNotNull(booking.getBookingID());
        assertEquals("STU001", booking.getStudentID());
        assertEquals("EVT001", booking.getEventID());
        assertNotNull(booking.getBookingDate());
        System.out.println("Created: " + booking);
    }

    @Test
    @Order(2)
    void testRead() {
        ResponseEntity<TicketBookingDetails> response = restTemplate.getForEntity(BASE_URL + "/" + booking.getBookingID(), TicketBookingDetails.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(booking.getBookingID(), response.getBody().getBookingID());
        assertEquals("STU001", response.getBody().getStudentID());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void testUpdate() {
        TicketBookingDetails updatedBooking = new TicketBookingDetails.Builder()
                .setBookingID(booking.getBookingID())
                .setStudentID("STU999")
                .setEventID(booking.getEventID())
                .setBookingDate(booking.getBookingDate())
                .build();

        restTemplate.put(BASE_URL, updatedBooking);

        ResponseEntity<TicketBookingDetails> response = restTemplate.getForEntity(BASE_URL + "/" + booking.getBookingID(), TicketBookingDetails.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("STU999", response.getBody().getStudentID());

        booking = response.getBody();
        System.out.println("Updated: " + booking);
    }

    @Test
    @Order(4)
    void testGetAll() {
        ResponseEntity<TicketBookingDetails[]> response = restTemplate.getForEntity(BASE_URL, TicketBookingDetails[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Bookings:");
        for (TicketBookingDetails b : response.getBody()) {
            System.out.println(b);
        }
    }

    @Test
    @Order(5)
    void testDelete() {
        restTemplate.delete(BASE_URL + "/" + booking.getBookingID());

        ResponseEntity<TicketBookingDetails> response = restTemplate.getForEntity(BASE_URL + "/" + booking.getBookingID(), TicketBookingDetails.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted Booking ID: " + booking.getBookingID());
    }

    @Test
    @Order(6)
    void testReadNonExistent() {
        ResponseEntity<TicketBookingDetails> response = restTemplate.getForEntity(BASE_URL + "/99999", TicketBookingDetails.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Read non-existent returned 404 as expected");
    }

    @Test
    @Order(7)
    void testUpdateNonExistent() {
        TicketBookingDetails nonExistent = new TicketBookingDetails.Builder()
                .setBookingID(99999L)
                .setStudentID("STU999")
                .setEventID("EVT999")
                .build();

        restTemplate.put(BASE_URL, nonExistent);

        ResponseEntity<TicketBookingDetails> response = restTemplate.getForEntity(BASE_URL + "/99999", TicketBookingDetails.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Update non-existent returned 404 as expected");
    }
}