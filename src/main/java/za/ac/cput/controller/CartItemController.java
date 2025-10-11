/*package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.CartItem;
import za.ac.cput.service.CartItemService;

import java.util.List;

@RestController
@RequestMapping("/RB3_Capstone-Project/cartItem")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<CartItem>> getAll() {
        List<CartItem> cartItems = cartItemService.getAll();
        if (cartItems.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cartItems);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getById(@PathVariable String id) {
        CartItem cartItem = cartItemService.read(id);
        if (cartItem != null) {
            return ResponseEntity.ok(cartItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // CREATE
    @PostMapping
    public ResponseEntity<CartItem> create(@RequestBody CartItem cartItem) {
        try {
            CartItem created = cartItemService.create(cartItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CartItem> update(@PathVariable String id, @RequestBody CartItem cartItem) {
        // Ensure the ID from the path is used
        if (!id.equals(cartItem.getCartItemId())) {
            return ResponseEntity.badRequest().build();
        }

        CartItem updated = cartItemService.update(cartItem);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
*/