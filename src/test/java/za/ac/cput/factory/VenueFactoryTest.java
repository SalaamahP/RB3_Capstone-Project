/*VenueFactoryTest.java
Venue Factory Test class
Author: Salaamah Peck (230207170)
Date: 18 May 2025
*/
/*package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Venue;

import static org.junit.jupiter.api.Assertions.*;

class VenueFactoryTest {
    @Test
    void createVenue() {
        Venue venue = VenueFactory.createVenue("Engineering Building", "District Six Campus", 250);

        assertNotNull(venue);
        assertEquals("Engineering Building", venue.getVenueName());
        assertEquals("District Six Campus", venue.getVenueLocation());
        assertEquals(250, venue.getCapacity());
    }

    @Test
    void createVenue_NoVenueName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Venue venue = VenueFactory.createVenue(null, "District Six Campus", 250);
        });
        assertEquals("Venue name cannot be empty", exception.getMessage());

    }

    @Test
    void createVenue_negativeCapacity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Venue venue = VenueFactory.createVenue("Engineering Building", "District Six Campus", -250);

        });
        assertEquals("Venue capacity cannot be negative", exception.getMessage());
    }
}
*/
