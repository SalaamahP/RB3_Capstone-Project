// Author: Jaedon Prince (230473474)
// Date: 11 May 2025
package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ticket_booking_details")
public class TicketBookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;

    @Column(nullable = false)
    private String studentID;

    @Column(nullable = false)
    private String eventID;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    protected TicketBookingDetails() {}

    private TicketBookingDetails(Builder builder) {
        this.bookingID = builder.bookingID;
        this.studentID = builder.studentID;
        this.eventID = builder.eventID;
        this.bookingDate = builder.bookingDate != null ? builder.bookingDate : LocalDateTime.now();
    }

    // GETTERS AND SETTERS - COMPLETE
    public Long getBookingID() { return bookingID; }
    public void setBookingID(Long bookingID) { this.bookingID = bookingID; }

    public String getStudentID() { return studentID; }
    public void setStudentID(String studentID) { this.studentID = studentID; }

    public String getEventID() { return eventID; }
    public void setEventID(String eventID) { this.eventID = eventID; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    @Override
    public String toString() {
        return "TicketBookingDetails{" +
                "bookingID=" + bookingID +
                ", studentID='" + studentID + '\'' +
                ", eventID='" + eventID + '\'' +
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

    public static class Builder {
        private Long bookingID;
        private String studentID;
        private String eventID;
        private LocalDateTime bookingDate;

        public Builder setBookingID(Long bookingID) {
            this.bookingID = bookingID;
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

        public Builder setBookingDate(LocalDateTime bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public TicketBookingDetails build() {
            return new TicketBookingDetails(this);
        }
    }
}

