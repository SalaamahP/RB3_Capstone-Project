/* Cart.java
 * Cart POJO class
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 11 May 2025
 */
package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private String userId;

    @Enumerated(EnumType.STRING)
    private PaymentOption paymentOption;

    private LocalDateTime bookingDate;

    public Long getCartId() { return cartId; }
    public String getUserId() { return userId; }
    public PaymentOption getPaymentOption() { return paymentOption; }
    public LocalDateTime getBookingDate() { return bookingDate; }

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
        private String userId;
        private PaymentOption paymentOption;
        private LocalDateTime bookingDate;

        public Builder setUserId(String userId) { this.userId = userId; return this; }
        public Builder setPaymentOption(PaymentOption paymentOption) { this.paymentOption = paymentOption; return this; }
        public Builder setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; return this; }

        public Builder copy(Cart cart) {
            this.userId = cart.getUserId();
            this.paymentOption = cart.getPaymentOption();
            this.bookingDate = cart.getBookingDate();
            return this;
        }

        public Cart build() {
            Cart cart = new Cart();
            cart.userId = this.userId;
            cart.paymentOption = this.paymentOption;
            cart.bookingDate = this.bookingDate;
            return cart;
        }
    }

    public enum PaymentOption { CASH, DEPOSIT }
}
