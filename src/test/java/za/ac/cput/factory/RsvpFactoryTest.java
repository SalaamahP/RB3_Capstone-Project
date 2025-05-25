/*
 * CartItemFactoryTest.java
 * Rsvp Factory POJO class
 * Author: Patience Phakathi (222228431)
 * Date: 18/05/2025
 */

package za.ac.cput.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Rsvp;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Event;
import za.ac.cput.domain.Rsvp;
import za.ac.cput.domain.Student;

import static org.junit.jupiter.api.Assertions.*;

class RsvpFactoryTest {

    private Student createTestStudent() {
        Student student = new Student();
        student.setStudentId("S12345");
        return student;
    }

    private Event createTestEvent() {
        Event event = new Event();
        event.setEventId("E67890");
        return event;
    }

    @Test
    void testCreateRsvp_Success() {
        Student student = createTestStudent();
        Event event = createTestEvent();

        Rsvp rsvp = RsvpFactory.createRsvp(student, event, "Confirmed");
        assertNotNull(rsvp);
        assertNotNull(rsvp.getRsvpID());
        assertEquals(student, rsvp.getStudent());
        assertEquals(event, rsvp.getEvent());
        assertEquals(Rsvp.Status.CONFIRMED, rsvp.getStatus());
    }

    @Test
    void testCreateRsvp_InvalidInputs() {
        Event event = createTestEvent();
        Student student = createTestStudent();

        assertNull(RsvpFactory.createRsvp(null, event, "Confirmed"));
        assertNull(RsvpFactory.createRsvp(student, null, "Confirmed"));
        assertNull(RsvpFactory.createRsvp(student, event, null));
        assertNull(RsvpFactory.createRsvp(student, event, ""));
        assertNull(RsvpFactory.createRsvp(student, event, "InvalidStatus"));
    }
}
