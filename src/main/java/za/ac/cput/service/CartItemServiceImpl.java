package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.CartItem;
import za.ac.cput.repository.CartItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public abstract class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository repository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return repository.save(cartItem);
    }

    @Override
    public Optional<CartItem> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<CartItem> findAll() {
        return repository.findAll();
    }

    @Override
    public List<CartItem> findByStudentId(String studentId) {
        return repository.findByStudent_StudentId(studentId);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
