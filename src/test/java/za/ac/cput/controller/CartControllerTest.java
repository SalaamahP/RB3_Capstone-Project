package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Cart.PaymentOption;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String BASE_URL;

    private static Cart cart;

    @BeforeEach
    void init() {
        BASE_URL = "http://localhost:" + port + "/SEMS/cart";

    }

    @BeforeAll
    static void setUp() {
        cart = new Cart.Builder()
                .setUserId("user123")
                .setPaymentOption(PaymentOption.CASH)
                .setBookingDate(LocalDateTime.now())
                .build();
    }

    @Test
    @Order(1)
    void create() {
        ResponseEntity<Cart> response = restTemplate.postForEntity(BASE_URL + "/create", cart, Cart.class);
        assertNotNull(response.getBody());
        cart = response.getBody();
        assertTrue(cart.getCartId() > 0);
        System.out.println("Created Cart ID: " + cart.getCartId());
    }

    @Test
    @Order(2)
    void read() {
        ResponseEntity<Cart> response = restTemplate.getForEntity(BASE_URL + "/read/" + cart.getCartId(), Cart.class);
        assertNotNull(response.getBody());
        assertEquals(cart.getCartId(), response.getBody().getCartId());
        System.out.println("Read Cart ID: " + response.getBody().getCartId());
    }

    @Test
    @Order(3)
    void update() {
        Cart updatedCart = new Cart.Builder()
                .copy(cart)
                .setPaymentOption(PaymentOption.CASH)
                .build();

        restTemplate.put(BASE_URL + "/update", updatedCart);

        ResponseEntity<Cart> response = restTemplate.getForEntity(BASE_URL + "/read/" + cart.getCartId(), Cart.class);
        assertNotNull(response.getBody());
        assertEquals(PaymentOption.CASH, response.getBody().getPaymentOption());
        System.out.println("Updated Cart ID: " + response.getBody().getCartId());
        cart = response.getBody();
    }

    @Test
    @Order(4)
    void getAll() {
        ResponseEntity<Cart[]> response = restTemplate.getForEntity(BASE_URL + "/getAll", Cart[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Carts:");
        for (Cart c : response.getBody()) {
            System.out.println(c);
        }
    }

    @Test
    @Order(5)
    void delete() {
        restTemplate.delete(BASE_URL + "/delete/" + cart.getCartId());

        ResponseEntity<Cart> response = restTemplate.getForEntity(BASE_URL + "/read/" + cart.getCartId(), Cart.class);
        assertNull(response.getBody());
        System.out.println("Deleted Cart ID: " + cart.getCartId());
    }
}
