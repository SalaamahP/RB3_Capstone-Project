/*AdminServiceImpl.java
Admin Service class
Author: Oratile Phologane (230690939)
Date: 25 May 2025
*/
package za.ac.cput.service;

import org.springframework.stereotype.Service;
import za.ac.cput.domain.Admin;
import za.ac.cput.repository.AdminRepository;

import java.util.List;
import java.util.Optional;


@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin read(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        if (adminRepository.existsById(admin.getId())) {
            return adminRepository.save(admin);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Admin> getAll() {
        return (List<Admin>) adminRepository.findAll();
    }

}
