/*package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Student;
import za.ac.cput.factory.StudentFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    private static Student student;

    @BeforeAll
    static void setUp(){
         student = StudentFactory.createStudent(
                "123", "123456","Alice","Green","0856459277","alice@gmail.com"
        );

    }

    @Test
    @Order(1)
    void add() {
        Student addStudent = studentService.create(student);
        assertNotNull(addStudent.getId());
        assertEquals("Alice", addStudent.getName());
        student=addStudent;
    }

    @Test
    @Order(2)
    void read(){
        Student readStudent = studentService.read(student.getId());
        assertNotNull(readStudent);
        assertEquals(student.getStudentNumber(), readStudent.getStudentNumber());
    }

    @Test
    @Order(3)
    void update(){
        Student updateStudent = new Student.Builder()
                .copy(student)
                .setName("Amy")
                .build();
        Student updated = studentService.update(updateStudent);
        assertEquals("Amy", updated.getName());

    }

    @Test
    @Order(5)
    void delete(){
        boolean deleted = studentService.delete(student.getId());
        assertTrue(deleted);
        assertNull(studentService.read(student.getId()));

    }

    @Test
    @Order(4)
    void getAll(){
        List<Student> students = studentService.getAll();
        assertNotNull(students);
    }
}

 */