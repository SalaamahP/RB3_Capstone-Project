/* AdminRepositoryTestTest.java
 * AdminRepository Test class
 * Author: Oratile Phologane (230690939)
 * Date: 25 May 2025
 */
package za.ac.cput.Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Admin.AdminRole;
import za.ac.cput.repository.AdminRepository;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminRepositoryTest {
    @Autowired
    private AdminRepository adminRepository;

    private Admin admin;


    @BeforeEach
    public void setUp() {
        adminRepository.deleteAll();
         admin = new Admin.Builder()
                .setName("Molefe")
                .setSurname("Tshabalala")
                .setPhone("0609505896")
                .setEmail("tshabalala@gmail.com")
                .setAdminRole(AdminRole.ADMIN)
                .build();
         admin = adminRepository.save(admin);

    }

    @Test
    void testSaveAdmin() {
        assertNotNull(admin.getId()); // Ensure ID is generated
        assertEquals("Molefe", admin.getName());

    }

    @Test
    void testFindById() {
        Optional<Admin> foundAdmin = adminRepository.findById(admin.getId());
        assertTrue(foundAdmin.isPresent());
        assertEquals("Molefe", foundAdmin.get().getName());
    }

    @Test
    void testFindAll() {
        List<Admin> admins = adminRepository.findAll();
        assertFalse(admins.isEmpty());
        assertEquals(1, admins.size());
        assertEquals("Molefe", admins.get(0).getName());
    }

}
