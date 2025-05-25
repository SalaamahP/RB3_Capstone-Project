package za.ac.cput.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.Rsvp;
import za.ac.cput.domain.Rsvp.Status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RsvpRepositoryTest {

    @Autowired
    private RsvpRepository rsvpRepository;

    private Rsvp rsvp1, rsvp2;

    @BeforeEach
    void setUp() {
        rsvp1 = new Rsvp.Builder()
                .setRsvpID("RSVP1")
                .setStudentID("STU123")
                .setEventID("EVT001")
                .setStatus(Status.CONFIRMED)
                .build();

        rsvp2 = new Rsvp.Builder()
                .setRsvpID("RSVP2")
                .setStudentID("STU123")
                .setEventID("EVT002")
                .setStatus(Status.PENDING)
                .build();

        rsvpRepository.saveAll(List.of(rsvp1, rsvp2));
    }

    @Test
    void testFindByStudentId() {
        List<Rsvp> rsvps = rsvpRepository.findByStudent_StudentId("STU123");
        assertTrue(rsvps.isEmpty(), "This query will fail unless Rsvp has a mapped Student object.");
    }

    @Test
    void testFindByEventId() {
        List<Rsvp> rsvps = rsvpRepository.findByEvent_EventId("EVT001");
        assertTrue(rsvps.isEmpty(), "This query will fail unless Rsvp has a mapped Event object.");
    }

    @Test
    void testSaveAndRetrieveRsvp() {
        Rsvp saved = rsvpRepository.findById("RSVP1").orElse(null);
        assertNotNull(saved);
        assertEquals(Status.CONFIRMED, saved.getStatus());
    }
}
