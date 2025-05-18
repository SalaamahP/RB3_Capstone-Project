/*
 * CartItemFactoryTest.java
 * CartItemFactory POJO class
 * Author: Patience Phakathi (222228431)
 * Date: 18/05/2025
 */

package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Student;

import static org.junit.jupiter.api.Assertions.*;

public class CartItemFactoryTest {

    // Helper method to create a dummy Student object
    private Student createTestStudent() {
        Student student = new Student();
        // Set at least the ID, assuming a setStudentId method exists
        // If your Student class uses a builder, update this accordingly
        student.setStudentId("STU123"); // You may need to make setter public in Student
        return student;
    }

    @Test
    void testCreateValidCartItem() {
        String productId = "PROD001";
        int quantity = 3;
        Student student = createTestStudent();

        CartItem cartItem = CartItemFactory.createCartItem(productId, quantity, student);

        assertNotNull(cartItem);
        assertNotNull(cartItem.getCartItemId());
        assertEquals(productId, cartItem.getProductId());
        assertEquals(quantity, cartItem.getQuantity());
        assertEquals(student, cartItem.getStudent());
    }

    @Test
    void testCreateCartItemWithNullProductId() {
        CartItem cartItem = CartItemFactory.createCartItem(null, 2, createTestStudent());
        assertNull(cartItem);
    }

    @Test
    void testCreateCartItemWithEmptyProductId() {
        CartItem cartItem = CartItemFactory.createCartItem("   ", 2, createTestStudent());
        assertNull(cartItem);
    }

    @Test
    void testCreateCartItemWithZeroQuantity() {
        CartItem cartItem = CartItemFactory.createCartItem("PROD002", 0, createTestStudent());
        assertNull(cartItem);
    }

    @Test
    void testCreateCartItemWithNullStudent() {
        CartItem cartItem = CartItemFactory.createCartItem("PROD003", 1, null);
        assertNull(cartItem);
    }
}
