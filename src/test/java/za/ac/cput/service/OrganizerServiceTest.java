/*package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Organizer;
import za.ac.cput.domain.Organizer.OrganizerType;
import za.ac.cput.factory.OrganizerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class OrganizerServiceTest {

    @Autowired
    private OrganizerService organizerService;

    private static Organizer organizer;

    @BeforeAll
    static void setUp() {
        organizer = OrganizerFactory.createOrganizer(
                "Xorg123", "Siphokazi", "Kile", "0123456789", "kile@gmail.com", OrganizerType.STUDENT_CLUB);

    }

    @Test
    @Order(1)
    void add() {
        Organizer addOrganizer = organizerService.create(organizer);
        assertNotNull(addOrganizer.getId());
        assertEquals("Siphokazi", addOrganizer.getName());
        organizer = addOrganizer;
    }

    @Test
    @Order(2)
    void read() {
        Organizer readOrganizer = organizerService.read(organizer.getId());
        assertNotNull(readOrganizer);
        assertEquals(organizer.getEmail(), readOrganizer.getEmail());
    }

    @Test
    @Order(3)
    void update() {
        Organizer updateOrganizer = new Organizer.Builder()
                .copy(organizer)
                .setName("Adams")
                .build();
        Organizer updated = organizerService.update(updateOrganizer);
        assertEquals("Adams", updated.getName());
    }

    @Test
    @Order(5)
    void delete() {
        boolean deletedOrganizer = organizerService.delete(organizer.getId());
        assertTrue(deletedOrganizer);
        assertNull(organizerService.read(organizer.getId()));
    }

    @Test
    @Order(4)
    void getAll() {
        List<Organizer> organizerList = organizerService.getAll();
        assertNotNull(organizerList);
        //System.out.println("Organizer List: " + organizerList);
    }
}
*/