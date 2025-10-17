package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.User;
import za.ac.cput.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private static IUserService service;

    UserRepository repository;
    @Autowired UserService(UserRepository repository) {
        this.repository = repository;
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

    public User findByEmailOrStudentOrStaff(String id){
        return repository.findByEmail(id)
                .or(() -> repository.findByStudentNumber(id))
                .or(() -> repository.findByStaffNumber(id))
                .orElse(null);


    }
}
