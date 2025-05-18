
/*
 * CartItemFactory.java
 * CartItem POJO class
 * Author: Patience Phakathi (222228431)
 * Date: 18/05/2025
 */

package za.ac.cput.factory;

import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Student;

import java.util.UUID;

public class CartItemFactory {

    /**
     * Creates a valid CartItem instance.
     *
     * @param productId The ID of the product
     * @param quantity  The quantity of the product
     * @param student   The student associated with this cart item
     * @return A CartItem object or null if validation fails
     */
    public static CartItem createCartItem(String productId, int quantity, Student student) {
        if (productId == null || productId.trim().isEmpty() || quantity <= 0 || student == null) {
            return null; // Validation failed
        }

        return new CartItem.Builder()
                .setCartItemId(UUID.randomUUID().toString())
                .setProductId(productId)
                .setQuantity(quantity)
                .setStudent(student)
                .build();
    }
}
