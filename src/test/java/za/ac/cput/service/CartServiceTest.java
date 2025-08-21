package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Cart;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart.Builder()
                .setUserId("user123")
                .setPaymentOption(Cart.PaymentOption.CASH)
                .setBookingDate(LocalDateTime.now())
                .build();
        cart = cartService.create(cart);
    }

    @Test
    void testCreateAndRead() {
        Cart readCart = cartService.read(cart.getCartId());
        assertNotNull(readCart);
        assertEquals(cart.getUserId(), readCart.getUserId());
    }

    @Test
    void testUpdate() {
        Cart updatedCart = new Cart.Builder()
                .setCartId(cart.getCartId())
                .setUserId("user456")
                .setPaymentOption(Cart.PaymentOption.DEPOSIT)
                .setBookingDate(cart.getBookingDate())
                .build();

        Cart saved = cartService.update(updatedCart);
        assertEquals("user456", saved.getUserId());
        assertEquals(Cart.PaymentOption.DEPOSIT, saved.getPaymentOption());
    }

    @Test
    void testGetAll() {
        List<Cart> carts = cartService.getAll();
        assertFalse(carts.isEmpty());
    }

    @Test
    void testDelete() {
        boolean deleted = cartService.delete(cart.getCartId());
        assertTrue(deleted, "Cart should be deleted successfully");
        assertNull(cartService.read(cart.getCartId()), "Cart should not be found after deletion");
    }
}
