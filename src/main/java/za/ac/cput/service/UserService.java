package za.ac.cput.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.domain.UserRole;
import za.ac.cput.repository.RoleRepository;
import za.ac.cput.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private static IUserService service;


    RoleRepository roleRepository;


    UserRepository repository;

    @Autowired
    UserService(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }


    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User read(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public User update(User user) {
        return this.repository.save(user);
    }

    @Override
    public boolean delete(Long id) {
        this.repository.deleteById(id);
        return true;
    }

    @Override
    public List<User> getAll() {
        return this.repository.findAll();
    }

    public User findByEmailOrStudentOrStaff(String id) {
        if (id == null || id.isBlank()) {
            System.err.println("id is null or empty");
            return null;
        }

        return repository.findByEmail(id)
                .or(() -> repository.findByStudentNumber(id))
                .or(() -> repository.findByStaffNumber(id))
                .orElse(null);
    }
}
