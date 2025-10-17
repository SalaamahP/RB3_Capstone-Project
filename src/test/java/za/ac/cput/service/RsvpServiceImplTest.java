/*package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Enum.Status;
import za.ac.cput.domain.Rsvp;

import za.ac.cput.factory.RsvpFactory;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RsvpServiceImplTest {

    @Autowired
    private RsvpService service;


    private static Rsvp rsvp;

    @BeforeAll
    static void setUp() {

        rsvp = RsvpFactory.createRsvp(Status.PENDING, LocalDate.now());
    }

    @Test
    @Order(1)
    void create() {
        Rsvp created = service.create(rsvp);
        assertNotNull(created);
        assertEquals(rsvp.getRsvpId(), created.getRsvpId());
        System.out.println("Created: " + created);
        rsvp = created;

    }

    @Test
    @Order(2)
    void read() {
        Rsvp read = service.read(rsvp.getRsvpId());
        assertNotNull(read);
        assertEquals(rsvp.getRsvpId(), read.getRsvpId());
        System.out.println("Read: " + read);

    }

    @Test
    @Order(3)
    void update() {
        Rsvp updated = new Rsvp.Builder()
                .copy(rsvp)
                .setStatus(Status.PENDING)
                .build();
        updated = service.update(updated);
        assertNotNull(updated);
        assertEquals(Status.PENDING, updated.getStatus());
        System.out.println("Updated: " + updated);

    }

    @Test
    @Order(4)
    void getAll() {
        System.out.println("All RSVPs: ");
        service.getAll().forEach(System.out::println);
        assertNotNull(service.getAll());
    }


}*/