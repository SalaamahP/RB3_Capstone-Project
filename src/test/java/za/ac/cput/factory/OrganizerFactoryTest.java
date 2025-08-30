/*OrganizerFactoryTesy.java
OrganizerFactoryTest class
Author: OP Phologane (230690939)
Date: 18/05/2025 */
package za.ac.cput.factory;

 import org.junit.jupiter.api.*;
 import za.ac.cput.domain.Organizer;
 import za.ac.cput.util.Helper;
 import za.ac.cput.domain.Organizer.OrganizerType;

 import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganizerFactoryTest {
    @Test
    @Order(1)
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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        Organizer organizer = OrganizerFactory.createOrganizer("dcvbdjnj57", "Oratile", null, "0789456696", "phologaneoratile@gmail.com", OrganizerType.STUDENT_CLUB);
        assertNull(organizer);
        });
        assertFalse(exception.getMessage().contains("Surname is invalid"));

    }

    @Test
    void isValidEmail() {
        assertTrue(Helper.isValidEmail("mariana@mycput.ac.za"));
    }

    @Test
    void isValidPhone() {
        assertTrue(Helper.isValidPhone("0789456696"));
    }

    @Test
    void testOrganizerInvalidPhoneNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> OrganizerFactory.createOrganizer("ndfnjdf899e", "Mike", "Pottery", "675258964", "potterym@gmail.com", OrganizerType.CORPORATION));
        assertEquals("Phone number is invalid", exception.getMessage());
    }

    @Test
    void testOrganizerInvalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Organizer organizer = OrganizerFactory.createOrganizer("ndfnjdf899e", "Mike", "Pottery", "0789456696", "potterymgmail.com", OrganizerType.CORPORATION);
        });
        assertEquals("Email is invalid", exception.getMessage());
    }

}
