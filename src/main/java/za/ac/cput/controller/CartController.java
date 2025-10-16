package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Cart;
import za.ac.cput.service.ICartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final ICartService ICartService;

    @Autowired
    public CartController(ICartService ICartService) {
        this.ICartService = ICartService;
    }

    @PostMapping("/create")
    public ResponseEntity<Cart> create(@RequestBody Cart cart) {
        return ResponseEntity.ok(ICartService.create(cart));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Cart> read(@PathVariable Long id) {
        Cart cart = ICartService.read(id);
        return ResponseEntity.ok(cart); 
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> update(@RequestBody Cart cart) {
        Cart updated = ICartService.update(cart);
        return ResponseEntity.ok(updated); 
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ICartService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Cart>> getAll() {
        return ResponseEntity.ok(ICartService.getAll());
    }
}
