
/*Rsvp.java
Rsvp class
Author: Patience Phakathi (222228431)
Date: 11/05/2025
 */

package za.ac.cput.domain;

import jakarta.persistence.*;
import java.util.Objects;


@Entity
public class Rsvp {

    @Id
    private String rsvpID;
    private String studentID;
    private String eventID;

    @Enumerated(EnumType.STRING)
    private Status status;


    public enum Status {
        CONFIRMED, PENDING, DECLINED
    }

    public Rsvp() {
        // Default constructor required by JPA
    }

    private Rsvp(Builder builder) {
        this.rsvpID = builder.rsvpID;
        this.studentID = builder.studentID;
        this.eventID = builder.eventID;
        this.status = builder.status;
    }

    public String getRsvpID() {
        return rsvpID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getEventID() {
        return eventID;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rsvp)) return false;
        Rsvp rsvp = (Rsvp) o;
        return Objects.equals(rsvpID, rsvp.rsvpID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rsvpID);
    }

    @Override
    public String toString() {
        return "Rsvp{" +
                "rsvpID='" + rsvpID + '\'' +
                ", studentID='" + studentID + '\'' +
                ", eventID='" + eventID + '\'' +
                ", status=" + status +
                '}';
    }


    public static class Builder {
        private String rsvpID;
        private String studentID;
        private String eventID;
        private Status status;

        public Builder setRsvpID(String rsvpID) {
            this.rsvpID = rsvpID;
            return this;
        }

        public Builder setStudentID(String studentID) {
            this.studentID = studentID;
            return this;
        }

        public Builder setEventID(String eventID) {
            this.eventID = eventID;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Rsvp build() {
            return new Rsvp(this);
        }
    }
}
