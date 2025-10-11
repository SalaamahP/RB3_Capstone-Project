/*Admin.java
Admin POJO class
Author: OP Phologane (230690939)
Date: 11/05/2025
 */
/* za.ac.cput.domain;


import jakarta.persistence.*;

import java.util.List;


@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {

    public enum AdminRole {
        SYSTEM_ADMIN,
        ADMIN, STAFF
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "Admin_Role", nullable = true)
    private AdminRole adminRole;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private List<User> users; // Admin can manage multiple users



    public Admin() {

    }

    private Admin(Builder builder) {
        super(builder.password, builder.name, builder.surname, builder.phone, builder.email);
        this.id = builder.id;
        this.adminRole = builder.adminRole;
    }

    public AdminRole getAdminRole() {
        return adminRole;
    }

    @Override
    public String toString() {
        return "Admin{" +
                " id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", adminRole=" + adminRole +
                '}';
    }

    public static class Builder {
        private long id;
        private String password;
        private String name;
        private String surname;
        private String phone;
        private String email;
        private AdminRole adminRole;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setAdminRole(AdminRole adminRole) {
            this.adminRole = adminRole;
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

        public Builder copy(Admin admin) {
            this.id = admin.id;
            this.password = admin.password;
            this.name = admin.name;
            this.surname = admin.surname;
            this.phone = admin.phone;
            this.email = admin.email;
            this.adminRole = admin.adminRole;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }
    }
}
*/