package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Cart.PaymentOption;
import za.ac.cput.service.CartService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart.Builder()
                .setUserId("123L")
                .setPaymentOption(PaymentOption.CASH)
                .setBookingDate(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateCart() throws Exception {
        Mockito.when(cartService.create(any(Cart.class))).thenReturn(cart);

        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cart)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(cart.getUserId()));
    }

    @Test
    void testReadCart() throws Exception {
        Mockito.when(cartService.read("123L")).thenReturn(cart);

        mockMvc.perform(get("/api/cart/123L"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(cart.getUserId()));
    }

    @Test
    void testReadCartNotFound() throws Exception {
        Mockito.when(cartService.read("999L")).thenReturn(null);

        mockMvc.perform(get("/api/cart/999L"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCart() throws Exception {
        Mockito.when(cartService.update(any(Cart.class))).thenReturn(cart);

        mockMvc.perform(put("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cart)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(cart.getUserId()));
    }

    @Test
    void testUpdateCartNotFound() throws Exception {
        Mockito.when(cartService.update(any(Cart.class))).thenReturn(null);

        mockMvc.perform(put("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cart)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCart() throws Exception {
        Mockito.when(cartService.delete("123L")).thenReturn(true);

        mockMvc.perform(delete("/api/cart/123L"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCartNotFound() throws Exception {
        Mockito.when(cartService.delete("999L")).thenReturn(false);

        mockMvc.perform(delete("/api/cart/999L"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllCarts() throws Exception {
        Mockito.when(cartService.getAll()).thenReturn(Arrays.asList(cart));

        mockMvc.perform(get("/api/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(cart.getUserId()));
    }
}