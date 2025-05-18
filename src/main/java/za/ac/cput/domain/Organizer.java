/*Organizer.java
Admin POJO class
Author: OP Phologane (230690939)
Date: 10/05/2025
 */
package za.ac.cput.domain;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

//@Entity
//@DiscriminatorValue("Organizer")
public class Organizer extends User {
    public enum OrganizerType {
        STUDENT_CLUB,
        FACULTY,
        CORPORATION
    }

    @Enumerated(EnumType.STRING)
    private OrganizerType organizerType;

    public Organizer() {
        super();
    }

    private Organizer(Builder builder) {
        this.id = builder.id;
        this.password = builder.password;
        this.name = builder.name;
        this.surname = builder.surname;
        this.phone = builder.phone;
        this.email = builder.email;
        this.organizerType = builder.organizerType;
    }

    public OrganizerType getOrganizerType() {
        return organizerType;
    }

    @Override
    public String toString() {
        return "Organizer{" +
                " id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", organizerType=" + organizerType +
                '}';
    }

    public static class Builder {
        private long id;
        private String password;
        private String name;
        private String surname;
        private String phone;
        private String email;
        private OrganizerType organizerType;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder setOrganizerType(OrganizerType organizerType) {
            this.organizerType = organizerType;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder copy(Organizer organizer) {
            this.password = organizer.password;
            this.name = organizer.name;
            this.surname = organizer.surname;
            this.phone = organizer.phone;
            this.email = organizer.email;
            this.organizerType = organizer.organizerType;
            return this;
        }

        public Organizer build() {
            return new Organizer(this);
        }
    }
}
