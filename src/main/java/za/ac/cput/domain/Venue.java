/*Venue.java
Venue POJO class
Author: S Peck (230207170)
Date: 11/05/2025
 */
package za.ac.cput.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long venueId;
    private String venueName;
    private String venueLocation;
    private int capacity;

    @OneToMany(mappedBy = "venue")
    private List<Event> events;

    private Venue (Builder builder){
        this.venueId = builder.venueId;
        this.venueName = builder.venueName;
        this.venueLocation = builder.venueLocation;
        this.capacity = builder.capacity;

    }

    public Long getVenueId() {
        return venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getVenueLocation() {
        return venueLocation;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "venueId=" + venueId +
                ", venueName='" + venueName + '\'' +
                ", venueLocation='" + venueLocation + '\'' +
                ", capacity=" + capacity +
                '}';
    }

    public static class Builder {
        private Long venueId;
        private String venueName;
        private String venueLocation;
        private int capacity;

        public Builder setVanueId(Long venueId) {
            this.venueId = venueId;
            return this;
        }
        public Builder setVenueName(String venueName) {
            this.venueName = venueName;
            return this;
        }
        public Builder setVenueLocation(String venueLocation) {
            this.venueLocation = venueLocation;
            return this;
        }
        public Builder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }
        public  Builder copy (Venue venue){
            this.setVanueId(venue.getVenueId());
            this.setVenueName(venue.getVenueName());
            this.setVenueLocation(venue.getVenueLocation());
            this.setCapacity(venue.getCapacity());
            return this;
        }

        public Venue build() {
            return new Venue(this);
        }

    }
}
