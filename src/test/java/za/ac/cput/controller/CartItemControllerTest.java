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

import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Student;
import za.ac.cput.service.CartItemService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartItemController.class)
public class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartItemService cartItemService;

    @Autowired
    private ObjectMapper objectMapper;

    private CartItem cartItem;

    @BeforeEach
    void setup() {
        Student student = new Student.Builder()
                .setId("student1")
                .setName("Test Student")
                .build();

        cartItem = new CartItem.Builder()
                .setCartItemId("cartItem1")
                .setProductId("product123")
                .setQuantity(5)
                .setStudent(student)
                .build();
    }

    @Test
    void testGetAll() throws Exception {
        Mockito.when(cartItemService.findAll()).thenReturn(Arrays.asList(cartItem));

        mockMvc.perform(get("/api/cartitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cartItemId").value(cartItem.getCartItemId()));
    }

    @Test
    void testGetById_found() throws Exception {
        Mockito.when(cartItemService.findById("cartItem1")).thenReturn(Optional.of(cartItem));

        mockMvc.perform(get("/api/cartitems/cartItem1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItemId").value(cartItem.getCartItemId()));
    }

    @Test
    void testGetById_notFound() throws Exception {
        Mockito.when(cartItemService.findById("cartItem2")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cartitems/cartItem2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        Mockito.when(cartItemService.save(any(CartItem.class))).thenReturn(cartItem);

        mockMvc.perform(post("/api/cartitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItemId").value(cartItem.getCartItemId()));
    }

    @Test
    void testUpdate_found() throws Exception {
        Mockito.when(cartItemService.update(eq("cartItem1"), any(CartItem.class))).thenReturn(Optional.of(cartItem));

        mockMvc.perform(put("/api/cartitems/cartItem1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItemId").value(cartItem.getCartItemId()));
    }

    @Test
    void testUpdate_notFound() throws Exception {
        Mockito.when(cartItemService.update(eq("cartItem2"), any(CartItem.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/cartitems/cartItem2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItem)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_found() throws Exception {
        Mockito.when(cartItemService.deleteById("cartItem1")).thenReturn(true);

        mockMvc.perform(delete("/api/cartitems/cartItem1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_notFound() throws Exception {
        Mockito.when(cartItemService.deleteById("cartItem2")).thenReturn(false);

        mockMvc.perform(delete("/api/cartitems/cartItem2"))
                .andExpect(status().isNotFound());
    }
}
