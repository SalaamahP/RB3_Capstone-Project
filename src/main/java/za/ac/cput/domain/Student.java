/*Student.java
Student POJO class
Author: S Peck (230207170)
Date: 10/05/2025
 */
package za.ac.cput.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("Student")
public class Student extends User{

    private String studentNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Student() {
    }

    public Student (Builder builder) {
        super(builder.password, builder.name, builder.surname, builder.phone, builder.email);
        this.studentNumber = builder.studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }



    @Override
    public String toString() {
        return "Student{" +
                "studentNumber='" + studentNumber + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static class Builder {
        private String studentNumber;
        private String password;
        private String name;
        private String surname;
        private String phone;
        private String email;

        public Builder setStudentNumber(String studentNumber){
            this.studentNumber = studentNumber;
            return this;

        }
        public Builder setPassword(String password){
            this.password = password;
            return this;
        }
        public Builder setName(String name){
            this.name = name;
            return this;
        }
        public Builder setSurname(String surname){
            this.surname = surname;
            return this;
        }
        public Builder setPhone(String phone){
            this.phone = phone;
            return this;
        }
        public Builder setEmail(String email){
            this.email = email;
            return this;
        }
        public Builder copy(Student student){
            this.studentNumber = student.studentNumber;
            this.name = student.name;
            this.surname = student.surname;
            this.phone = student.phone;
            this.email = student.email;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }


}

