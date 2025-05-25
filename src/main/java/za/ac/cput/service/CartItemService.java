package za.ac.cput.service;

import za.ac.cput.domain.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    CartItem save(CartItem cartItem);
    List<CartItem> findAll();

    Optional<CartItem> findById(String id);

    Optional<CartItem> update(String id, CartItem cartItem);
    boolean deleteById(String id); // If you're using this in the controller

    // Add the following to fix @Override errors:
    List<CartItem> findByStudentId(String studentId);
    void delete(String id); // If you're overriding this method
}

