package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Student;
import za.ac.cput.factory.StudentFactory;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentControllerTest {

    private static Student student;

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private  String BASE_URL; //= 8080/SEMS/student";

   @BeforeEach
   void init() {
       BASE_URL = "http://localhost:" + port + "/SEMS/student";
   }


    @BeforeAll
    public static void setUp() {
        student = StudentFactory.createStudent("123", "pass123", "Bob", "Smith", "0821673099", "bob@gmail.com");
    }

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Student> postResponse = restTemplate.postForEntity(url, student, Student.class);
        assertNotNull(postResponse.getBody());

        student = postResponse.getBody();
        assertEquals("Bob", student.getName());
        System.out.println("Created: " + student.getName());

    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + student.getId();
        ResponseEntity<Student> response = restTemplate.getForEntity(url, Student.class);
        assertEquals(student.getId(), Objects.requireNonNull(response.getBody()).getId());
        System.out.println("Read: " + student.getId());
    }

    @Test
    @Order(3)
    void update() {
       assertNotNull(student.getId());
        Student updatedStudent = new Student.Builder().copy(student).setName("Robert").build();

        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedStudent);

        //Verify update by reading updated
        ResponseEntity<Student> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + student.getId(), Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedStudent.getName(), response.getBody().getName());
        System.out.println("Updated Student Name: " + response.getBody());

        student = response.getBody();

    }

    @Test
    @Order(5)
    void delete() {
        String url = BASE_URL + "/delete/" + student.getId();
        this.restTemplate.delete(url);

        //Verify venue was deleted
        ResponseEntity<Student> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + student.getId(), Student.class);
        assertNull(response.getBody());
        System.out.println("Deleted: " + student.getId());

    }

    @Test
    @Order(4)
    void getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Student[]> response = this.restTemplate.getForEntity(url, Student[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All:");
        for (Student student : response.getBody()) {
            System.out.println(student);
        }
    }
}
