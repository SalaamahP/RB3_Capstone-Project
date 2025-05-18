/*OrganizerFactoryTesy.java
OrganizerFactoryTest class
Author: OP Phologane (230690939)
Date: 18/05/2025 */
package za.ac.cput.factory;

 import org.junit.jupiter.api.Test;
 import za.ac.cput.domain.Organizer;
 import za.ac.cput.domain.Organizer.OrganizerType;

 import static org.junit.jupiter.api.Assertions.*;


public class OrganizerFactoryTest {
    @Test
    void testCreateOrganizer() {
        Organizer organizer = OrganizerFactory.createOrganizer("whsvh78s", "Tshipo", "Mokaila", "0681234569", "mokaila@gmail.com", OrganizerType.STUDENT_CLUB);
        assertNotNull(organizer);
        assertEquals("whsvh78s", organizer.getPassword());
        assertEquals("Tshipo", organizer.getName());
        assertEquals("Mokaila", organizer.getSurname());
        assertEquals("0681234569", organizer.getPhone());
        assertEquals("mokaila@gmail.com", organizer.getEmail());
        assertEquals(OrganizerType.STUDENT_CLUB, organizer.getOrganizerType());

    }

    @Test
    void testOrganizerWithNullValue() {
        Organizer organizer = OrganizerFactory.createOrganizer("dcvbdjnj57", "Oratile", "", "0789456696", "phologaneoratile@gmail.com", OrganizerType.STUDENT_CLUB);
        assertNull(organizer, "Organizer should be null due to empty surname");

    }

    @Test
    void testOrganizerType() {
        Organizer organizer = OrganizerFactory.createOrganizer("ndfnjdf899e", "Mike", "Pottery", "0675258964", "potterym@gmail.com", OrganizerType.CORPORATION);
        assertNotEquals(OrganizerType.STUDENT_CLUB, organizer.getOrganizerType());
    }

    @Test
    void testOrganizerInvalidPhoneNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> OrganizerFactory.createOrganizer("ndfnjdf899e", "Mike", "Pottery", "675258964", "potterym@gmail.com", OrganizerType.CORPORATION));
        assertEquals("Phone number is invalid", exception.getMessage(), "Exception message match");
    }

}
