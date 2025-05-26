package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.cput.domain.Organizer;
import za.ac.cput.domain.Organizer.OrganizerType;
import za.ac.cput.repository.OrganizerRepository;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class OrganizerServiceTest {

    @Mock
    private OrganizerRepository organizerRepository;

    @InjectMocks
    private OrganizerServiceImpl organizerService;

    private Organizer organizer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        organizer = new Organizer.Builder()
                .setName("Mary")
                .setSurname("Smith")
                .setPhone("0693692590")
                .setEmail("smithm@gmail.com")
                .setPassword("k13/admin")
                .setOrganizerType(OrganizerType.STUDENT_CLUB)
                .build();
        organizerRepository.save(organizer);
        System.out.println(organizer.getId());
    }

    @Test
    void testCreateOrganizer() {
        when(organizerRepository.save(any(Organizer.class))).thenAnswer(invocation -> {
            Organizer savedOrganizer = invocation.getArgument(0);
            savedOrganizer = new Organizer.Builder().copy(savedOrganizer).id(2L).build();// Simulate ID generation
            return savedOrganizer;
        });

        Organizer createdOrganizer = organizerService.create(organizer);
        assertNotNull(createdOrganizer);
        assertEquals(OrganizerType.STUDENT_CLUB, createdOrganizer.getOrganizerType());
    }

    @Test
    void testReadOrganizer() {
        when(organizerRepository.findById(anyLong())).thenReturn(Optional.of(organizer));
        Organizer readOrganizer = organizerService.read(2L);
        assertNotNull(readOrganizer);
        assertEquals("Mary", readOrganizer.getName());
    }

    @Test
    void testUpdateOrganizer() {
        Organizer updatedOrganizer = new Organizer.Builder()
                .copy(organizer)
                .setName("John")
                .build();
        when(organizerRepository.save(any(Organizer.class))).thenReturn(updatedOrganizer);

        Organizer result = organizerService.update(updatedOrganizer);
        assertNotNull(result);
        assertEquals("John", result.getName());
    }

    @Test
    void testDeleteOrganizer() {
        when(organizerRepository.findById(anyLong())).thenReturn(Optional.of(organizer));
        organizerService.delete(2L);
        verify(organizerRepository, times(1)).deleteById(2L);
    }

    @Test
    void testGetAllOrganizers() {
        when(organizerRepository.findAll()).thenReturn(List.of(organizer));
        List<Organizer> organizers = organizerService.getAll();
        assertNotNull(organizers);
        assertEquals(1, organizers.size());
        assertEquals("smithm@gmail.com", organizers.get(0).getEmail());
    }
}
