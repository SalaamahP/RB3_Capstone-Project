/*package za.ac.cput.factory;

import za.ac.cput.domain.Enum.Status;
import za.ac.cput.domain.Rsvp;
import za.ac.cput.domain.Student;
import za.ac.cput.util.Helper;

import java.time.LocalDate;

public class RsvpFactory {

    public static Rsvp createRsvp(Status status , LocalDate rsvpDate) {
        if (status == null) {
           return  null;
        }

        String rsvpId = Helper.generateId();
        return new Rsvp.Builder()
                .setRsvpId(rsvpId)
                .setStatus(status)
                .setRsvpDate(rsvpDate)
                .build();
    }



}

 */