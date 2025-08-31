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
        assertTrue(savedCart.getCartId() > 0);

        Cart foundCart = cartRepository.findById(savedCart.getCartId()).orElse(null);
        assertNotNull(foundCart);
        assertEquals(savedCart.getCartId(), foundCart.getCartId());
    }

    @Test
    void testDeleteById() {
        Cart cart = new Cart.Builder()
                .setUserId("user123")
                .setPaymentOption(Cart.PaymentOption.CASH)
                .setBookingDate(LocalDateTime.now())
                .build();

        Cart savedCart = cartRepository.save(cart);
        cartRepository.deleteById(savedCart.getCartId());

        assertFalse(cartRepository.existsById(savedCart.getCartId()));
    }
}
