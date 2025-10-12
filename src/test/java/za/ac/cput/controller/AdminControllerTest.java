/* AdminControllerTest.java
    * AdminControllerTest class
    * Author: Oratile Phologane (230690969)
    * Date: 25 May 2025
 */
/*package za.ac.cput.controller;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Admin;
import za.ac.cput.factory.AdminFactory;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminControllerTest {
    private static Admin admin;

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    private String BASE_URL; //= 8080/SEMS/admin";

    @BeforeEach
    void init() {
        BASE_URL = "http://localhost:" + port + "/SEMS/admin";
    }

    @BeforeAll
    public static void setUp() {
        admin = AdminFactory.createAdmin("Pass3990", "Lebogang", "Mooki", "0809654127", "lebogang@gmail.com", Admin.AdminRole.ADMIN);

    }

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Admin> postResponse = restTemplate.postForEntity(url, admin, Admin.class);
        assertNotNull(postResponse.getBody());

        admin = postResponse.getBody();
        assertEquals("Lebogang", admin.getName());
        System.out.println("Created: " + admin.getName());

    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + admin.getId();
        ResponseEntity<Admin> response = restTemplate.getForEntity(url, Admin.class);
        assertEquals(admin.getId(), Objects.requireNonNull(response.getBody()).getId());
        System.out.println("Read: " + admin.getId());
    }

    @Test
    @Order(3)
    void update() {
        assertNotNull(admin.getId());
        Admin updatedAdmin = new Admin.Builder()
                .copy(admin)
                .setName("Karabo")
                .build();

        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedAdmin);

        //Verify update by reading updated
        ResponseEntity<Admin> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + admin.getId(), Admin.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedAdmin.getName(), response.getBody().getName());
        System.out.println("Updated Admin Name: " + response.getBody());

        admin = response.getBody();
    }

    @Test
    @Order(4)
    void getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(url, Admin[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All: ");
        for (Admin admin : response.getBody()) {
            System.out.println(admin);
        }
    }

    @Test
    @Order(5)
    void delete() {
        String url = BASE_URL + "/delete/" + admin.getId();
        this.restTemplate.delete(url);

        //Verify admin was deleted
        ResponseEntity<Admin> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + admin.getId(), Admin.class);
        assertNull(response.getBody());
        System.out.println("Deleted: " + admin.getId());
    }

}
*/