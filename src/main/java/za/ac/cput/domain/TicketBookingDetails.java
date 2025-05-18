// Author: Jaedon Prince (230473474)
// Date: 11 May 2025

package za.ac.cput.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class TicketBookingDetails {
    private final String bookingId;
    private final long userId;
    private final String eventId;
    private final int quantity;
    private final double total;
    private final LocalDateTime dateBooked;
    private final PaymentOption paymentSelection;
    private final Status status;

    public enum PaymentOption {
        DEPOSIT, CASH
    }

    public enum Status {
        PENDING, CONFIRMED, CANCELLED
    }

    private TicketBookingDetails(Builder builder) {
        this.bookingId = builder.bookingId;
        this.userId = builder.userId;
        this.eventId = builder.eventId;
        this.quantity = builder.quantity;
        this.total = builder.total;
        this.dateBooked = builder.dateBooked;
        this.paymentSelection = builder.paymentSelection;
        this.status = builder.status;
    }

    public String getBookingId() { return bookingId; }
    public long getUserId() { return userId; }
    public String getEventId() { return eventId; }
    public int getQuantity() { return quantity; }
    public double getTotal() { return total; }
    public LocalDateTime getDateBooked() { return dateBooked; }
    public PaymentOption getPaymentSelection() { return paymentSelection; }
    public Status getStatus() { return status; }

    public static class Builder {
        private String bookingId = UUID.randomUUID().toString(); // auto-generated
        private long userId;
        private String eventId;
        private int quantity;
        private double total;
        private LocalDateTime dateBooked = LocalDateTime.now();
        private PaymentOption paymentSelection;
        private Status status;

        public Builder setUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setTotal(double total) {
            this.total = total;
            return this;
        }

        public Builder setDateBooked(LocalDateTime dateBooked) {
            this.dateBooked = dateBooked;
            return this;
        }

        public Builder setPaymentSelection(PaymentOption paymentSelection) {
            this.paymentSelection = paymentSelection;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public TicketBookingDetails build() {
            return new TicketBookingDetails(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketBookingDetails)) return false;
        TicketBookingDetails that = (TicketBookingDetails) o;
        return Objects.equals(bookingId, that.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }

    @Override
    public String toString() {
        return "TicketBookingDetails{" +
                "bookingId='" + bookingId + '\'' +
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
