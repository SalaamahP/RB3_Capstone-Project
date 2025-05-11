/* Cart.java
 * Cart POJO class
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 11 May 2025
 */
package za.ac.cput.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;
    private String userId;

    @Enumerated(EnumType.STRING)
    private PaymentOption paymentOption; // Enum for payment options
    private LocalDateTime bookingDate;

    public Cart() {
    }

    private Cart(Builder builder) {
        this.cartId = builder.cartId;
        this.userId = builder.userId;
        this.paymentOption = builder.paymentOption;
        this.bookingDate = builder.bookingDate;
    }

    // Getters
    public long getCartId() {
        return cartId;
    }

    public String getUserId() {
        return userId;
    }

    public PaymentOption getPaymentOption() {
        return paymentOption;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId='" + userId + '\'' +
                ", paymentOption=" + paymentOption +
                ", bookingDate=" + bookingDate +
                '}';
    }

    public static class Builder {
        private long cartId;
        private String userId;
        private PaymentOption paymentOption;
        private LocalDateTime bookingDate;

        public Builder setCartId(long cartId) {
            this.cartId = cartId;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setPaymentOption(PaymentOption paymentOption) {
            this.paymentOption = paymentOption;
            return this;
        }

        public Builder setBookingDate(LocalDateTime bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }

    public enum PaymentOption {
        DEPOSIT,
        CASH
    }
}