package za.ac.cput.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Role;
import za.ac.cput.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    public static IRoleService service;
    private final RoleRepository roleRepository;

    RoleRepository repository;
    @Autowired RoleService(RoleRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }



    @PostConstruct
    public void init() {
        createIfNotExists("STUDENT","Default role for all new users");
        createIfNotExists("ADMIN","Administrator with CRUD privileges");
        createIfNotExists("ORGANIZER","Organizer of events");
    }

    private void createIfNotExists(String roleName, String description) {
        if(roleRepository.findRoleByRoleName(roleName) == null) {
            Role role = new Role.Builder()
                    .setRoleName(roleName)
                    .setDescription(description)
                    .build();
            roleRepository.save(role);
        }
    }

    @Override
    public Role create(Role role) {
        return repository.save(role);
    }

    @Override
    public Role read(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Role update(Role role) {
        return this.repository.save(role);
    }

    @Override
    public boolean delete(Long id) {
        this.repository.deleteById(id);
        return true;
    }

    @Override
    public List<Role> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Role findByRoleName(String roleName) {
      return repository.findRoleByRoleName(roleName);
    }

}
