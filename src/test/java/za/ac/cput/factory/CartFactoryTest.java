/*
 * CartFactoryTest.java
 * Test class for CartFactory
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 18 May 2025
 */
package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Cart.PaymentOption;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CartFactoryTest {
// Test case to verify the successful creation of a Cart object
    @Test
    void testCreateCartSuccess() {
        Cart cart = CartFactory.createCart("123L", PaymentOption.CASH, LocalDateTime.now());
        assertNotNull(cart); // Check if the cart object is not null
        assertEquals("123L", cart.getUserId());
        assertEquals(PaymentOption.CASH, cart.getPaymentOption());
        assertNotNull(cart.getBookingDate());
    }
// Test case to verify the exception thrown when an invalid user ID is provided
    @Test
    void testCreateCartWithInvalidUserId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                CartFactory.createCart("", PaymentOption.CASH, LocalDateTime.now()));
        assertEquals("User ID is required", exception.getMessage()); // verify the exception message
    }
//Test case to verify the exception thrown when a null payment option is provided
    @Test
    void testCreateCartWithNullPaymentOption() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                CartFactory.createCart("123L", null, LocalDateTime.now()));
        assertEquals("Payment option is required", exception.getMessage());
    }
// Test case to verify the exception thrown when a null booking date is provided
    @Test
    void testCreateCartWithNullBookingDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                CartFactory.createCart("123L", PaymentOption.CASH, null));
        assertEquals("Booking date is required", exception.getMessage());
    }
}