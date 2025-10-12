package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Cart.PaymentOption;
import za.ac.cput.factory.CartFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartServiceTest {

    @Autowired
    private CartService cartService;

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart.Builder()
                .setUserId("user123")
                .setPaymentOption(PaymentOption.CASH)
                .setBookingDate(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreate() {
        Cart created = cartService.create(cart);
        assertNotNull(created);
        assertNotNull(created.getCartId());
        System.out.println("Cart created: " + created);
    }

    @Test
    void testRead() {
        Cart created = cartService.create(cart);
        Cart found = cartService.read(created.getCartId());
        assertNotNull(found);
        assertEquals(created.getCartId(), found.getCartId());
        System.out.println("Cart read: " + found);
    }

    @Test
    void testUpdate() {
        Cart created = cartService.create(cart);
        Cart updatedCart = new Cart.Builder()
                .copy(created)
                .setPaymentOption(PaymentOption.DEPOSIT)
                .build();

        Cart updated = cartService.update(updatedCart);
        assertNotNull(updated);
        assertEquals(PaymentOption.DEPOSIT, updated.getPaymentOption());
        System.out.println("Cart updated: " + updated);
    }

    @Test
    void testDelete() {
        Cart created = cartService.create(cart);
        boolean deleted = cartService.delete(created.getCartId());
        assertTrue(deleted);
        System.out.println("Cart deleted successfully");
    }

    @Test
    void testGetAll() {
        List<Cart> carts = cartService.getAll();
        assertNotNull(carts);
        System.out.println("All carts: " + carts);
    }
}