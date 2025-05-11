// Author: Jaedon Prince (230473474)
// Date: 11 May 2025

package za.ac.cput.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class TicketBookingDetails {
    private long bookingId;
    private long userId;
    private String eventId;
    private int quantity;
    private double total;
    private LocalDateTime dateBooked;
    private PaymentOption paymentSelection;
    private Status status;

    public enum PaymentOption {
        DEPOSIT, CASH
    }

    public enum Status {
        PENDING, CONFIRMED, CANCELLED
    }

    public TicketBookingDetails() {}

    public TicketBookingDetails(long bookingId, long userId, String eventId, int quantity,
                                double total, LocalDateTime dateBooked,
                                PaymentOption paymentSelection, Status status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.quantity = quantity;
        this.total = total;
        this.dateBooked = dateBooked;
        this.paymentSelection = paymentSelection;
        this.status = status;
    }

    // Getters and Setters

    public long getBookingId() { return bookingId; }
    public void setBookingId(long bookingId) { this.bookingId = bookingId; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public LocalDateTime getDateBooked() { return dateBooked; }
    public void setDateBooked(LocalDateTime dateBooked) { this.dateBooked = dateBooked; }

    public PaymentOption getPaymentSelection() { return paymentSelection; }
    public void setPaymentSelection(PaymentOption paymentSelection) { this.paymentSelection = paymentSelection; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketBookingDetails)) return false;
        TicketBookingDetails that = (TicketBookingDetails) o;
        return bookingId == that.bookingId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }

    @Override
    public String toString() {
        return "TicketBookingDetails{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", eventId='" + eventId + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                ", dateBooked=" + dateBooked +
                ", paymentSelection=" + paymentSelection +
                ", status=" + status +
                '}';
    }
}
