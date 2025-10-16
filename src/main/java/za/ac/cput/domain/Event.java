/* Event.java
 * Event POJO class
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 11 May 2025
 */
package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;
    private String eventName;
    private String eventDescription;

    @Enumerated(EnumType.STRING)
    private EventCategory eventCategory;
    private LocalDateTime dateTime;
    private long venueId;
    private long userId;
    private double ticketPrice;

    public Event() {}

    private Event(Builder builder) {
        this.eventId = builder.eventId;
        this.eventName = builder.eventName;
        this.eventDescription = builder.eventDescription;
        this.eventCategory = builder.eventCategory;
        this.dateTime = builder.dateTime;
        this.venueId = builder.venueId;
        this.userId = builder.userId;
        this.ticketPrice = builder.ticketPrice;
    }

    // Getters
    public long getEventId() { return eventId; }
    public String getEventName() { return eventName; }
    public String getEventDescription() { return eventDescription; }
    public EventCategory getEventCategory() { return eventCategory; }
    public LocalDateTime getDateTime() { return dateTime; }
    public long getVenueId() { return venueId; }
    public long getUserId() { return userId; }
    public double getTicketPrice() { return ticketPrice; }

    // Setters
    public void setEventId(long eventId) { this.eventId = eventId; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }
    public void setEventCategory(EventCategory eventCategory) { this.eventCategory = eventCategory; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public void setVenueId(long venueId) { this.venueId = venueId; }
    public void setUserId(long userId) { this.userId = userId; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventCategory=" + eventCategory +
                ", dateTime=" + dateTime +
                ", venueId=" + venueId +
                ", userId=" + userId +
                ", ticketPrice=" + ticketPrice +
                '}';
    }

    public static class Builder {
        private long eventId;
        private String eventName;
        private String eventDescription;
        private EventCategory eventCategory;
        private LocalDateTime dateTime;
        private long venueId;
        private long userId;
        private double ticketPrice;

        public Builder setEventId(long eventId) { this.eventId = eventId; return this; }
        public Builder setEventName(String eventName) { this.eventName = eventName; return this; }
        public Builder setEventDescription(String eventDescription) { this.eventDescription = eventDescription; return this; }
        public Builder setEventCategory(EventCategory eventCategory) { this.eventCategory = eventCategory; return this; }
        public Builder setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; return this; }
        public Builder setVenueId(long venueId) { this.venueId = venueId; return this; }
        public Builder setUserId(long userId) { this.userId = userId; return this; }
        public Builder setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; return this; }

        public Event build() { return new Event(this); }

        public Builder copy(Event event) {
            this.eventId = event.eventId;
            this.eventName = event.eventName;
            this.eventDescription = event.eventDescription;
            this.eventCategory = event.eventCategory;
            this.dateTime = event.dateTime;
            this.venueId = event.venueId;
            this.userId = event.userId;
            this.ticketPrice = event.ticketPrice;
            return this;
        }
    }

    public enum EventCategory {
       ACADEMIC, SPORTS, CULTURAL, SOCIAL, WORKSHOP, CONFERENCE, ENTERTAINMENT, OTHER
    }
}