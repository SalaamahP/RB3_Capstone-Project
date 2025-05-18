/*StudentFactoryTest.java
Student Factory Test class
Author: Salaamah Peck (230207170)
Date: 18 May 2025
*/
package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.Student;
import za.ac.cput.util.Helper;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentFactoryTest {
    @Test
    @Order(1)
    void testCreateStudent() {
        Student student = StudentFactory.createStudent(
        "12345",
        "45678",
        "Jake",
        "Peralta",
        "0823578566",
        "peraltj@gmail.com"
        );
        assertNotNull(student);
        assertEquals("12345", student.getStudentNumber());
        assertEquals("45678", student.getPassword());
        assertEquals("Jake", student.getName());
        assertEquals("Peralta", student.getSurname());
        assertEquals("0823578566", student.getPhone());
        assertEquals("peraltj@gmail.com", student.getEmail());

    }

    @Test
        //Verify null details are not accepted
    void testCreateStudent_Fail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        Student student = StudentFactory.createStudent(null, "pass123" ,"John", "Cena", "jcenta@mycput.ac.za", "0811234567");
        assertNull(student);
        });
        assertTrue(exception.getMessage().contains("Student number is invalid"));
    }

    @Test
    void isValidEmail() {
        assertTrue(Helper.isValidEmail("jcena@mycput.ac.za"));
    }

    @Test
    void isValidPhone() {
        assertTrue(Helper.isValidPhone("0811234567"));
    }
    @Test
    void createStudent_invalidPhone() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Student student = StudentFactory.createStudent("12345", "pass123" ,"John", "Cena", "08112345", "jcena@gmail.com" );
        });

        assertEquals("Phone number is invalid", exception.getMessage());

    }
    @Test
    void createStudent_invalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Student student = StudentFactory.createStudent("12345", "pass123" ,"John", "Cena","0811234567", "jcena@gmail" );
        });

        assertEquals("Email is invalid", exception.getMessage());

    }
}