/*package za.ac.cput.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private StudentRepository studentRepository; // assuming you have this

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudent("STU123");
        studentRepository.save(student);

        CartItem item1 = new CartItem.Builder()
                .setCartItemId("CART1")
                .setProductId("PROD001")
                .setQuantity(2)
                .setStudent(student)
                .build();

        CartItem item2 = new CartItem.Builder()
                .setCartItemId("CART2")
                .setProductId("PROD002")
                .setQuantity(1)
                .setStudent(student)
                .build();

        cartItemRepository.saveAll(List.of(item1, item2));
    }

    @Test
    void testFindByStudentId() {
        List<CartItem> cartItems = cartItemRepository.findByStudent_StudentId("STU123");
        assertEquals(2, cartItems.size());
    }

    @Test
    void testSaveAndRetrieveCartItem() {
        CartItem saved = cartItemRepository.findById("CART1").orElse(null);
        assertNotNull(saved);
        assertEquals("PROD001", saved.getProductId());
    }
}*/
