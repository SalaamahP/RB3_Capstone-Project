/*RsvpFactory.java
RsvpFactory POJO class
Author: Patience Phakathi (222228431)
Date: 18/05/2025
 */

package za.ac.cput.factory;

import za.ac.cput.domain.Rsvp;
import java.util.UUID;

/**
 * Factory class for creating RSVP objects.
 */
public class RsvpFactory {

    /**
     * Creates a valid RSVP entry.
     *
     * @param studentID The ID of the student making the RSVP.
     * @param eventID The ID of the event.
     * @param status The RSVP status (CONFIRMED, PENDING, DECLINED).
     * @return A valid Rsvp object or null if input is invalid.
     */
    public static Rsvp createRsvp(String studentID, String eventID, String status) {
        // Validate inputs
        if (studentID == null || eventID == null || status == null ||
                studentID.trim().isEmpty() || eventID.trim().isEmpty() || status.trim().isEmpty()) {
            return null; // Ensure valid input
        }

        try {
            Rsvp.Status enumStatus = Rsvp.Status.valueOf(status.toUpperCase()); // Convert string to enum
            return new Rsvp.Builder()
                    .setRsvpID(UUID.randomUUID().toString()) // Generates a unique RSVP ID
                    .setStudentID(studentID)
                    .setEventID(eventID)
                    .setStatus(enumStatus) // Use enum instead of string
                    .build();
        } catch (IllegalArgumentException e) {
            return null; // Return null if the status is invalid
        }
    }
}
