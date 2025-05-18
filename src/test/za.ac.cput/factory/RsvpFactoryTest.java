/*
 * CartItemFactoryTest.java
 * Rsvp Factory POJO class
 * Author: Patience Phakathi (222228431)
 * Date: 18/05/2025
 */

package za.ac.cput.Factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Rsvp;

class RsvpFactoryTest {
    @Test
    void testCreateRsvp_Success() {
        Rsvp rsvp = RsvpFactory.createRsvp("S12345", "E67890", "Confirmed");
        Assertions.assertNotNull(rsvp);
        Assertions.assertNotNull(rsvp.getRsvpID());
        Assertions.assertEquals("S12345", rsvp.getStudentID());
        Assertions.assertEquals("E67890", rsvp.getEventID());
        Assertions.assertEquals("Confirmed", rsvp.getStatus());
    }

    @Test
    void testCreateRsvp_Fail_InvalidInputs() {
        Rsvp rsvp1 = RsvpFactory.createRsvp((String)null, "E67890", "Confirmed");
        Rsvp rsvp2 = RsvpFactory.createRsvp("S12345", "", "Confirmed");
        Rsvp rsvp3 = RsvpFactory.createRsvp("S12345", "E67890", (String)null);
        Assertions.assertNull(rsvp1, "Factory should return null when studentID is null.");
        Assertions.assertNull(rsvp2, "Factory should return null when eventID is empty.");
        Assertions.assertNull(rsvp3, "Factory should return null when status is null.");
    }
}
