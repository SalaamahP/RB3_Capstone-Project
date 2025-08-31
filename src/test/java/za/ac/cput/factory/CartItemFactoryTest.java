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

   private static CartItem cartItem = CartItemFactory.createCartItem("P1001", 2, new Student());

    @Test
    void test() {
        assertNotNull(cartItem);
        System.out.println(cartItem + " created");

    }

}
