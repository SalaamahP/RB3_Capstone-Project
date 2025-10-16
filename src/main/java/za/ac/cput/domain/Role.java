package za.ac.cput.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Role_Id")
    private Long roleId;

    @Column(name = "Role_Name", nullable = false, unique = true)
    private String roleName;

    @Column(name = "Description")
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    protected Role() {}

    private Role (Builder builder){
        this.roleId = builder.roleId;
        this.roleName = builder.roleName;
        this.description = builder.description;
    }

    public Long getRoleId() {
        return roleId;
    }


    public String getRoleName() {
        return roleName;
    }


    public String getDescription() {
        return description;
    }



    public Set<UserRole> getUserRoles() {
        return userRoles;
    }


    public static class Builder {
        private Long roleId;
        private String roleName;
        private String description;

        public Builder setRoleId(Long roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder setRoleName(String roleName) {
            this.roleName = roleName;
            return this;
        }
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder copy(Role role){
            this.roleId = role.roleId;
            this.roleName = role.roleName;
            this.description = role.description;
            return this;
        }
        public Role build(){
            return new Role(this);
        }
    }

    public void addUserRole(UserRole userRole) {
        userRoles.add(userRole);
        userRole.setRole(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", userRoles=" + userRoles +
                '}';
    }
}
