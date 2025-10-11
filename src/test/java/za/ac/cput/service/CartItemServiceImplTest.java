/*package za.ac.cput.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Student;
import za.ac.cput.factory.CartItemFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.class)
class CartItemServiceImplTest {

    @Autowired
    private  CartItemService cartItemService;

    private static CartItem cartItem = CartItemFactory.createCartItem("P1001", 2, new Student());


    @Test
    void create() {
        CartItem created = cartItemService.create(cartItem);
        assertNotNull(created);
        System.out.println("Created: " + created);
    }

    @Test
    void read() {
        CartItem read = cartItemService.read(cartItem.getCartItemId());
        assertNotNull(read);
        System.out.println("Read: " + read);
    }

    @Test
    void update() {
        CartItem updated = new CartItem.Builder()
                .copy(cartItem)
                .setQuantity(5)
                .build();
        assertNotNull(cartItemService.update(updated));
        System.out.println("Updated: " + updated);
    }

    @Test
    void getAll() {
        System.out.println("All CartItems: ");
        System.out.println(cartItemService.getAll());
        assertNotNull(cartItemService.getAll());
    }
}

 */