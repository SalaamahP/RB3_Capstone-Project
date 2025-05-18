// Author: Jaedon Prince (230473474)
// Date: 18 May 2025

package za.ac.cput.factory;

import za.ac.cput.domain.TicketBookingDetails;

public class TicketBookingDetailsFactory {

    /**
     * Create a new TicketBookingDetails via its Builder.
     *
     * @param userId           the ID of the user booking
     * @param eventId          the event's ID
     * @param quantity         number of tickets
     * @param total            total cost
     * @param paymentSelection DEPOSIT or CASH
     * @param status           PENDING, CONFIRMED, or CANCELLED
     * @return a fully built TicketBookingDetails instance
     */
    public static TicketBookingDetails createBooking(long userId,
                                                     String eventId,
                                                     int quantity,
                                                     double total,
                                                     TicketBookingDetails.PaymentOption paymentSelection,
                                                     TicketBookingDetails.Status status) {
        return new TicketBookingDetails.Builder()
                .setUserId(userId)
                .setEventId(eventId)
                .setQuantity(quantity)
                .setTotal(total)
                .setPaymentSelection(paymentSelection)
                .setStatus(status)
                .build();
    }
}
