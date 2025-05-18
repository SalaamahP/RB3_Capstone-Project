package za.ac.cput.factory;

import za.ac.cput.Util.Helper;
import za.ac.cput.domain.Student;

public class StudentFactory {

        public static Student createStudent(String studentNumber, String password,String name, String surname, String phone, String email) {
         if(Helper.isNullOrEmpty(studentNumber)){
             throw new IllegalArgumentException("Student number is invalid");
            }
            if(Helper.isNullOrEmpty(password)){
                throw new IllegalArgumentException("Password is invalid");
            }
            if(Helper.isNullOrEmpty(name)){
                throw new IllegalArgumentException("Name is invalid");
            }
            if(Helper.isNullOrEmpty(surname)){
                throw new IllegalArgumentException("Surname is invalid");
            }
            if(!Helper.isValidPhone(phone)){
                throw new IllegalArgumentException("Phone number is invalid");
            }
            if(!Helper.isValidEmail(email)){
                throw new IllegalArgumentException("Email is invalid");
            }

            return new Student.Builder()
                    .setStudentNumber(studentNumber)
                    .setPassword(password)
                    .setName(name)
                    .setSurname(surname)
                    .setPhone(phone)
                    .setEmail(email)
                    .build();
    }
}

