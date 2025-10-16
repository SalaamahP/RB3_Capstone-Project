package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User>findByEmail(String email);
    Optional<User>findByStudentNumber(String studentNumber);
    Optional<User>findByStaffNumber(String staffNumber);

}
