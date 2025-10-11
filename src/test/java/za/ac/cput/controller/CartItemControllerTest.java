/*package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.CartItem;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartItemControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/RB3_Capstone-Project/cartItem";

    private static CartItem testCartItem;
    private static String createdCartItemId;

    @BeforeAll
    static void setUp() {
        // Create a test CartItem using the builder pattern
        testCartItem = new CartItem.Builder()
                .setProductId("P101")
                .setQuantity(5)
                .build();
    }

    @Test
    @Order(1)
    void create() {
        System.out.println("Creating test cart item: " + testCartItem);

        ResponseEntity<CartItem> response = restTemplate.postForEntity(
                BASE_URL + "/create",
                testCartItem,
                CartItem.class
        );

        // ✅ Use the same pattern as CartControllerTest
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getCartItemId());

        createdCartItemId = response.getBody().getCartItemId();
        System.out.println("Created cart item with ID: " + createdCartItemId);
    }

    @Test
    @Order(2)
    void read() {
        // Skip if create failed
        if (createdCartItemId == null) {
            System.out.println("Skipping read test - no cart item ID available");
            return;
        }

        ResponseEntity<CartItem> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + createdCartItemId,
                CartItem.class
        );

        // ✅ Use the same pattern as CartControllerTest
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdCartItemId, response.getBody().getCartItemId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        if (createdCartItemId == null) {
            System.out.println("Skipping update test - no cart item ID available");
            return;
        }

        // First read the existing cart item
        ResponseEntity<CartItem> readResponse = restTemplate.getForEntity(
                BASE_URL + "/read/" + createdCartItemId,
                CartItem.class
        );

        // ✅ Use the same pattern as CartControllerTest
        assertEquals(HttpStatus.OK, readResponse.getStatusCode());
        assertNotNull(readResponse.getBody());

        CartItem cartItemToUpdate = readResponse.getBody();
        System.out.println("Before Update: " + cartItemToUpdate);

        // Update the cart item
        cartItemToUpdate.setQuantity(10);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CartItem> requestEntity = new HttpEntity<>(cartItemToUpdate, headers);

        ResponseEntity<CartItem> updateResponse = restTemplate.exchange(
                BASE_URL + "/update/" + createdCartItemId,
                HttpMethod.PUT,
                requestEntity,
                CartItem.class
        );

        // ✅ Use the same pattern as CartControllerTest
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals(10, updateResponse.getBody().getQuantity());
        System.out.println("After Update: " + updateResponse.getBody());
    }

    @Test
    @Order(4)
    void getAll() {
        ResponseEntity<CartItem[]> response = restTemplate.getForEntity(
                BASE_URL + "/getAll",
                CartItem[].class
        );

        // ✅ Use the same pattern as CartControllerTest
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("Get All: Found " + response.getBody().length + " cart items.");
    }

    @Test
    @Order(5)
    void delete() {
        if (createdCartItemId == null) {
            System.out.println("Skipping delete test - no cart item ID available");
            return;
        }

        // First create a new item to delete (to avoid affecting other tests)
        CartItem itemToDelete = new CartItem.Builder()
                .setProductId("P102")
                .setQuantity(3)
                .build();

        ResponseEntity<CartItem> createResponse = restTemplate.postForEntity(
                BASE_URL + "/create",
                itemToDelete,
                CartItem.class
        );

        // ✅ Use the same pattern as CartControllerTest
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());

        String itemIdToDelete = createResponse.getBody().getCartItemId();

        // Delete the item
        restTemplate.delete(BASE_URL + "/delete/" + itemIdToDelete);

        // Verify deletion - expect 404 NOT_FOUND
        ResponseEntity<CartItem> verifyResponse = restTemplate.getForEntity(
                BASE_URL + "/read/" + itemIdToDelete,
                CartItem.class
        );

        // ✅ Expect 404 after deletion
        assertEquals(HttpStatus.NOT_FOUND, verifyResponse.getStatusCode());
        System.out.println("Successfully deleted cart item with ID: " + itemIdToDelete);
    }
}
*/
