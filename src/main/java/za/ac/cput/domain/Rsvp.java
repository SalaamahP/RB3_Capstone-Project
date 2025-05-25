
/*Rsvp.java
Rsvp class
Author: Patience Phakathi (222228431)
Date: 11/05/2025
 */

package za.ac.cput.domain;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Rsvp {

    @Id
    private String rsvpID;


    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        CONFIRMED, PENDING, DECLINED
    }

    public Rsvp() {
        // Default constructor for JPA
    }

    private Rsvp(Builder builder) {
        this.rsvpID = builder.rsvpID;
        this.student = builder.student;
        this.event = builder.event;
        this.status = builder.status;
    }

    public String getRsvpID() {
        return rsvpID;
    }


    public Student getStudent() {
        return student;
    }

    public Event getEvent() {
        return event;
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
                ", student=" + student +
                ", event=" + event +
                ", status=" + status +
                '}';
    }

    public static class Builder {
        private String rsvpID;
        private Student student;
        private Event event;
        private Status status;

        public Builder setRsvpID(String rsvpID) {
            this.rsvpID = rsvpID;
            return this;
        }

        public Builder setStudent(Student student) {
            this.student = student;
            return this;
        }

        public Builder setEvent(Event event) {
            this.event = event;
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
