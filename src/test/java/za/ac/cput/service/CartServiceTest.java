package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.cput.domain.Cart;
import za.ac.cput.repository.CartRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private za.ac.cput.service.CartServiceImpl cartService;

    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart.Builder()
                .setUserId("user123")
                .setPaymentOption(Cart.PaymentOption.CASH)
                .setBookingDate(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreate() {
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart createdCart = cartService.create(cart);
        assertNotNull(createdCart);
        assertEquals("user123", createdCart.getUserId());

        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testRead() {
        when(cartRepository.findById("user123")).thenReturn(Optional.of(cart));

        Cart foundCart = cartService.read("user123");
        assertNotNull(foundCart);
        assertEquals("user123", foundCart.getUserId());

        verify(cartRepository, times(1)).findById("user123");
    }

    @Test
    void testUpdate() {
        when(cartRepository.existsById("user123")).thenReturn(true);
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart updatedCart = cartService.update(cart);
        assertNotNull(updatedCart);
        assertEquals("user123", updatedCart.getUserId());

        verify(cartRepository, times(1)).existsById("user123");
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testDelete() {
        when(cartRepository.existsById("user123")).thenReturn(true);

        boolean deleted = cartService.delete("user123");
        assertTrue(deleted);

        verify(cartRepository, times(1)).existsById("user123");
        verify(cartRepository, times(1)).deleteById("user123");
    }
}