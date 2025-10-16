package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(String roleName);
}
