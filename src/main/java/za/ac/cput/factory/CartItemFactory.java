
/*
 * CartItemFactory.java
 * CartItemFactory POJO class
 * Author: Patience Phakathi (222228431)
 * Date: 18/05/2025
 */

package za.ac.cput.factory;

import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Student;

import java.util.UUID;

public class CartItemFactory {

    public static CartItem createCartItem(String productId, int quantity, Student student) {
        if (productId == null || productId.trim().isEmpty() || quantity <= 0 || student == null) {
            return null; // Validation failed
        }

        return new CartItem.Builder()
                .setCartItemId(UUID.randomUUID().toString())
                .setProductId(productId)
                .setQuantity(quantity)
                //.setStudent(student)
                .build();
    }
}
