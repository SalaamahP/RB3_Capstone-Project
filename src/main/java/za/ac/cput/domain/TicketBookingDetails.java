// Author: Jaedon Prince (230473474)
// Date: 11 May 2025
package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TicketBookingDetails {

    @Id
    private String bookingID;
    private String eventId;
    private String studentId;
    private int numberOfTickets;
    private LocalDateTime bookingDate; // <-- new field

    // no-args constructor for JPA
    protected TicketBookingDetails() {}

    private TicketBookingDetails(Builder builder) {
        this.bookingID = builder.bookingID;
        this.eventId = builder.eventId;
        this.studentId = builder.studentId;
        this.numberOfTickets = builder.numberOfTickets;
        this.bookingDate = builder.bookingDate; // <-- assign from builder
    }

    public String getBookingID() {
        return bookingID;
    }

    public String getEventId() {
        return eventId;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public static class Builder {
        private String bookingID;
        private String eventId;
        private String studentId;
        private int numberOfTickets;
        private LocalDateTime bookingDate; // <-- new field in builder

        public Builder setBookingID(String bookingID) {
            this.bookingID = bookingID;
            return this;
        }

        public Builder setEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder setStudentId(String studentId) {
            this.studentId = studentId;
            return this;
        }

        public Builder setNumberOfTickets(int numberOfTickets) {
            this.numberOfTickets = numberOfTickets;
            return this;
        }

        public Builder setBookingDate(LocalDateTime bookingDate) { // <-- new setter
            this.bookingDate = bookingDate;
            return this;
        }

        public TicketBookingDetails build() {
            return new TicketBookingDetails(this);
        }
    }

    @Override
    public String toString() {
        return "TicketBookingDetails{" +
                "bookingID='" + bookingID + '\'' +
                ", eventId='" + eventId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", numberOfTickets=" + numberOfTickets +
                ", bookingDate=" + bookingDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketBookingDetails)) return false;
        TicketBookingDetails that = (TicketBookingDetails) o;
        return Objects.equals(bookingID, that.bookingID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingID);
    }
}

