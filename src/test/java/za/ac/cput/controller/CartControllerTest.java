/*
 * CartControllerTest.java
 * Integration test class for CartController
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 21 August 2025
 */
package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Cart.PaymentOption;
import za.ac.cput.factory.CartFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String BASE_URL;

    private static Cart cart;

    @BeforeAll
    public static void setUp() {
        // Create a Cart object using factory
        cart = CartFactory.createCart(
                "user123",
                PaymentOption.CASH,
                LocalDateTime.now()
        );
    }

    @BeforeEach
    void init() {
        BASE_URL = "http://localhost:" + port + "/api/cart";
    }

    @Test
    @Order(1)
    void create() {
        ResponseEntity<Cart> response = restTemplate.postForEntity(BASE_URL, cart, Cart.class);
        assertNotNull(response.getBody(), "Created cart should not be null");

        cart = response.getBody(); // Save the generated cartId for future tests
        assertEquals("user123", cart.getUserId(), "User ID should match the factory input");
        System.out.println("Created Cart: " + cart);
    }

    @Test
    @Order(2)
    void read() {
        ResponseEntity<Cart> response = restTemplate.getForEntity(BASE_URL + "/" + cart.getCartId(), Cart.class);
        assertNotNull(response.getBody(), "Read cart should not be null");
        assertEquals(cart.getCartId(), response.getBody().getCartId(), "Cart ID should match");
        System.out.println("Read Cart: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        // Update Cart details
        Cart updatedCart = new Cart.Builder()
                .setCartId(cart.getCartId())
                .setUserId("user456")
                .setPaymentOption(PaymentOption.DEPOSIT)
                .setBookingDate(cart.getBookingDate())
                .build();

        restTemplate.put(BASE_URL, updatedCart);

        ResponseEntity<Cart> response = restTemplate.getForEntity(BASE_URL + "/" + cart.getCartId(), Cart.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
        assertNotNull(response.getBody(), "Updated cart should not be null");
        assertEquals("user456", response.getBody().getUserId(), "User ID should be updated");

        cart = response.getBody(); // Save updated cart
        System.out.println("Updated Cart: " + cart);
    }

    @Test
    @Order(4)
    void getAll() {
        ResponseEntity<Cart[]> response = restTemplate.getForEntity(BASE_URL, Cart[].class);
        assertNotNull(response.getBody(), "GetAll should not return null");
        assertTrue(response.getBody().length > 0, "There should be at least one cart");
        System.out.println("All Carts:");
        for (Cart c : response.getBody()) {
            System.out.println(c);
        }
    }

    @Test
    @Order(5)
    void delete() {
        restTemplate.delete(BASE_URL + "/" + cart.getCartId());

        ResponseEntity<Cart> response = restTemplate.getForEntity(BASE_URL + "/" + cart.getCartId(), Cart.class);
        assertNull(response.getBody(), "Deleted cart should be null");
        System.out.println("Deleted Cart ID: " + cart.getCartId());
    }
}
