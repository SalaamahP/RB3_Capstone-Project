package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.Venue;
import za.ac.cput.factory.VenueFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VenueServiceTest {
    @Autowired
    private VenueService service;

    private static Venue venue;

    @BeforeAll
    static void setUp(){
        venue = VenueFactory.createVenue(
                "Admin Building","D6 Campus",100
        );


    }

    @Test
    @Order(1)
    void add() {
        Venue addVenue = service.create(venue);
        assertNotNull(addVenue.getVenueId());
        assertEquals("Admin Building", addVenue.getVenueName());
        venue = addVenue;

    }

    @Test
    @Order(2)
    void read(){
        Venue readVenue = service.read(venue.getVenueId());
        assertNotNull(readVenue.getVenueId());
        assertEquals("Admin Building", readVenue.getVenueName());

    }

    @Test
    @Order(3)
    void update(){
        Venue updateVenue = new Venue.Builder()
                .copy(venue)
                .setCapacity(50)
                .build();

        Venue updatedVenue = service.update(updateVenue);
        assertEquals(50, updatedVenue.getCapacity());



    }

    @Test
    @Order(5)
    void delete(){
        boolean deleted = service.delete(venue.getVenueId());
        assertTrue(deleted);
        assertNull(service.read(venue.getVenueId()));

    }

    @Test
    @Order(4)
    void getAll(){
        List<Venue> venues = service.getAll();
        assertNotNull(venues);

    }

}