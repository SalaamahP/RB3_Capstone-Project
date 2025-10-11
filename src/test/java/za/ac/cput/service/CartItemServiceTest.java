/*package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Student;
import za.ac.cput.factory.CartItemFactory;
import za.ac.cput.repository.CartItemRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CartItemServiceTest {

    private CartItemRepository repository;
    private CartItemServiceImpl service;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        repository = mock(CartItemRepository.class);
        service = new CartItemServiceImpl(repository) {
            @Override
            public Optional<CartItem> update(String id, CartItem cartItem) {
                return Optional.empty();
            }

            @Override
            public boolean deleteById(String id) {
                return false;
            }
        };
        Student student = new Student();
        student.setStudentId("S1001");
        cartItem = CartItemFactory.createCartItem("PROD001", 2, student);
    }

    @Test
    void testSave() {
        when(repository.save(cartItem)).thenReturn(cartItem);
        CartItem saved = service.save(cartItem);
        assertNotNull(saved);
        verify(repository).save(cartItem);
    }

    @Test
    void testFindById() {
        when(repository.findById(cartItem.getCartItemId())).thenReturn(Optional.of(cartItem));
        CartItem found = service.findById(cartItem.getCartItemId());
        assertNotNull(found);
        assertEquals(cartItem.getCartItemId(), found.getCartItemId());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(cartItem.getCartItemId());
        service.delete(cartItem.getCartItemId());
        verify(repository).deleteById(cartItem.getCartItemId());
    }
}
*/