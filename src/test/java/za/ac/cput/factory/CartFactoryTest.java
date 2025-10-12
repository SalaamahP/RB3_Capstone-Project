/*
 * CartFactoryTest.java
 * Test class for CartFactory
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 18 May 2025
 */
package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Cart.PaymentOption;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartFactoryTest {

    @Test
    @Order(1)
    void testCreateCartSuccess() {
        Cart cart = CartFactory.createCart("123L", PaymentOption.CASH, LocalDateTime.now());
        assertNotNull(cart);
        assertEquals("123L", cart.getUserId());
        assertEquals(PaymentOption.CASH, cart.getPaymentOption());
        assertNotNull(cart.getBookingDate());
        System.out.println("Cart created successfully: " + cart);
    }

    @Test
    @Order(2)
    void testCreateCartWithInvalidUserId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                CartFactory.createCart("", PaymentOption.CASH, LocalDateTime.now()));
        assertEquals("User ID is required", exception.getMessage());
        System.out.println("Invalid UserId check passed");
    }

    @Test
    @Order(3)
    void testCreateCartWithNullPaymentOption() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                CartFactory.createCart("123L", null, LocalDateTime.now()));
        assertEquals("Payment option is required", exception.getMessage());
        System.out.println("Null PaymentOption check passed");
    }

    @Test
    @Order(4)
    void
    testCreateCartWithNullBookingDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                CartFactory.createCart("123L", PaymentOption.CASH, null));
        assertEquals("Booking date is required", exception.getMessage());
        System.out.println("Null BookingDate check passed");
    }
}
