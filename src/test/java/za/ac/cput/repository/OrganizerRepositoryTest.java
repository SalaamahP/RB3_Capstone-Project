/* OrganizerRepositoryTestTest.java
 * OrganizerRepository Test class
 * Author: Oratile Phologane (230690939)
 * Date: 25 May 2025
 */
/*package za.ac.cput.Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Organizer;
import za.ac.cput.domain.Organizer.OrganizerType;
import za.ac.cput.repository.OrganizerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrganizerRepositoryTest {
    @Autowired
    private OrganizerRepository organizerRepository;

    private Organizer organizer;

    @BeforeEach
    void setUp() {
        organizerRepository.deleteAll();
        organizer = new Organizer.Builder()
                .setName("Zoe")
                .setSurname("Doherty")
                .setPhone("0996644852")
                .setEmail("doherty@cput.ac.za")
                .setOrganizerType(OrganizerType.FACULTY)
                .build();
        organizer = organizerRepository.save(organizer);
    }

    @Test
    void testSave() {
        Organizer savedOrganizer = organizerRepository.save(organizer);
        assertNotNull(savedOrganizer);
        assertEquals(organizer.getName(), savedOrganizer.getName());
    }

    @Test
    void testFindById() {
        Optional<Organizer> foundOrganizer = organizerRepository.findById(organizer.getId());
        assertTrue(foundOrganizer.isPresent());
        assertEquals("Zoe", foundOrganizer.get().getName());
    }
    @Test
    void testFindAll() {
        List<Organizer> organizers = StreamSupport.stream(organizerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        assertFalse(organizers.isEmpty());
        assertEquals(1, organizers.size());
    }

}
*/
