package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.Cart;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    void testSaveAndFindById() {
        Cart cart = new Cart.Builder()
                .setUserId("user123")
                .setPaymentOption(Cart.PaymentOption.CASH)
                .setBookingDate(LocalDateTime.now())
                .build();

        Cart savedCart = cartRepository.save(cart);
        assertNotNull(savedCart);
        assertEquals("user123", savedCart.getUserId());

        Cart foundCart = cartRepository.findById("user123").orElse(null);
        assertNotNull(foundCart);
        assertEquals("user123", foundCart.getUserId());
    }

    @Test
    void testDeleteById() {
        Cart cart = new Cart.Builder()
                .setUserId("user123")
                .setPaymentOption(Cart.PaymentOption.CASH)
                .setBookingDate(LocalDateTime.now())
                .build();

        cartRepository.save(cart);
        cartRepository.deleteById("user123");

        assertFalse(cartRepository.existsById("user123"));
    }
}
