package za.ac.cput.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;


public class UserFactory {


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static User createUser(String name, String surname, String email, String password, String phone, String studentNumber, String staffNumber) {

        if (Helper.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (Helper.isNullOrEmpty(surname)) {
            throw new IllegalArgumentException("Surname cannot be null or empty");
        }
        if (!Helper.isValidEmail(email)) {
            throw new IllegalArgumentException("Email is invalid");
        }
        if (Helper.isNullOrEmpty(password)) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (!Helper.isValidPhone(phone)) {
            throw new IllegalArgumentException("Phone number is invalid");
        }
        if (!Helper.isValidEmail(email)) {
            throw new IllegalArgumentException("Email is invalid");
        }
        if (!Helper.isNullOrEmpty(studentNumber) && studentNumber.length() != 9) {
            throw new IllegalArgumentException("Student number is invalid");
        }
        if (!Helper.isNullOrEmpty(staffNumber) && staffNumber.length() != 9) {
            throw new IllegalArgumentException("Staff number is invalid");
        }

        String encodedPassword = passwordEncoder.encode(password);

        return new User.Builder()
                .setName(name)
                .setSurname(surname)
                .setEmail(email)
                .setPassword(encodedPassword)
                .setPhone(phone)
                .setStudentNumber(studentNumber)
                .setStaffNumber(staffNumber)
                .build();
    }
}

