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
    private ICartService ICartService;

    private static Cart cart;

    @BeforeAll
    static void setUp() {
        cart = CartFactory.createCart(
                "user123",
                PaymentOption.CASH,
                LocalDateTime.now()
        );
    }

    @Test
    @Order(1)
    void create() {
        Cart created = ICartService.create(cart);
        assertNotNull(created);
        assertEquals("user123", created.getUserId());
        cart = created; // store the cart with generated cartId
        System.out.println("Created Cart ID: " + cart.getCartId());
    }

    @Test
    @Order(2)
    void read() {
        Cart readCart = ICartService.read(cart.getCartId());
        assertNotNull(readCart);
        assertEquals(cart.getUserId(), readCart.getUserId());
        System.out.println("Read Cart ID: " + readCart.getCartId());
    }

    @Test
    @Order(3)
    void update() {
        Cart updatedCart = new Cart.Builder()
                .copy(cart)
                .setPaymentOption(PaymentOption.DEPOSIT)
                .build();

        Cart updated = ICartService.update(updatedCart);
        assertNotNull(updated);
        assertEquals(PaymentOption.DEPOSIT, updated.getPaymentOption());
        System.out.println("Updated Cart ID: " + updated.getCartId() + ", Payment Option: " + updated.getPaymentOption());

        cart = updated;
    }

    @Test
    @Order(4)
    void getAll() {
        List<Cart> carts = ICartService.getAll();
        assertNotNull(carts);
        assertFalse(carts.isEmpty());
        System.out.println("All Carts: " + carts);
    }

    @Test
    @Order(5)
    void delete() {
        boolean deleted = ICartService.delete(cart.getCartId());
        assertTrue(deleted);

        Cart readAfterDelete = ICartService.read(cart.getCartId());
        assertNull(readAfterDelete); // ensure it is deleted
        System.out.println("Deleted Cart ID: " + cart.getCartId());
    }
}
