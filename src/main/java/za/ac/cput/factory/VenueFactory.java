package za.ac.cput.factory;

import za.ac.cput.util.Helper;
/*VenueFactory.java
Venue Factory class
Author: Salaamah Peck (230207170)
Date: 18 May 2025
*/

import za.ac.cput.domain.Venue;

public class VenueFactory {

    public static Venue createVenue(String venueName, String venueLocation, int venueCapacity) {
        if (Helper.isNullOrEmpty(venueName)) {
            throw new IllegalArgumentException("Venue name cannot be empty");
        }
        if (Helper.isNullOrEmpty(venueLocation)) {
            throw new IllegalArgumentException("Venue location cannot be empty");

        }
        if (venueCapacity < 0) {
            throw new IllegalArgumentException("Venue capacity cannot be negative");
        }
        return new Venue.Builder()
                .setVenueName(venueName)
                .setVenueLocation(venueLocation)
                .setCapacity(venueCapacity)
                .build();
    }
}
