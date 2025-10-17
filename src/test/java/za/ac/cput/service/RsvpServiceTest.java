/*package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Rsvp;
import za.ac.cput.factory.RsvpFactory;
import za.ac.cput.repository.RsvpRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RsvpServiceTest {

    private RsvpRepository repository;
    private RsvpServiceImpl service;
    private Rsvp rsvp;

    @BeforeEach
    void setUp() {
        repository = mock(RsvpRepository.class);
        service = new RsvpServiceImpl(repository);
        rsvp = RsvpFactory.createRsvp("S12345", "E67890", "CONFIRMED");
    }

    @Test
    void testSave() {
        when(repository.save(rsvp)).thenReturn(rsvp);
        Rsvp saved = service.save(rsvp);
        assertNotNull(saved);
        verify(repository).save(rsvp);
    }

    @Test
    void testFindById() {
        when(repository.findById(rsvp.getRsvpID())).thenReturn(Optional.of(rsvp));
        Rsvp found = service.findById(rsvp.getRsvpID());
        assertNotNull(found);
        assertEquals(rsvp.getRsvpID(), found.getRsvpID());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(rsvp.getRsvpID());
        service.delete(rsvp.getRsvpID());
        verify(repository).deleteById(rsvp.getRsvpID());
    }

    @Test
    void testFindByStudentId() {
        when(repository.findByStudent_StudentId("S12345")).thenReturn(List.of(rsvp));
        List<Rsvp> list = service.findByStudentId("S12345");
        assertFalse(list.isEmpty());
    }

    @Test
    void testFindByEventId() {
        when(repository.findByEvent_EventId("E67890")).thenReturn(List.of(rsvp));
        List<Rsvp> list = service.findByEventId("E67890");
        assertFalse(list.isEmpty());
    }
}
*/
