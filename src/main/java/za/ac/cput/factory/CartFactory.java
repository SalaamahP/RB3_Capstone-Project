/*
 * CartFactory.java
 * Factory class for Event
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 18 May 2025
 */
/*
// * CartFactory.java
// * Factory class for Event
// * Author: Nobahle Vuyiswa Nzimande (222641533)
// * Date: 18 May 2025
*/
package za.ac.cput.factory;

import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Cart.PaymentOption;

import java.time.LocalDateTime;

public class CartFactory {

    public static Cart createCart(String userId, PaymentOption paymentOption, LocalDateTime bookingDate) {

        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }

        if (paymentOption == null) {
            throw new IllegalArgumentException("Payment option is required");
        }

        if (bookingDate == null) {
            throw new IllegalArgumentException("Booking date is required");
        }

        return new Cart.Builder()
                .setUserId(userId)
                .setPaymentOption(paymentOption)
                .setBookingDate(bookingDate)
                .build();
    }
}
