/*AdminServiceImpl.java
Admin Service class
Author: Oratile Phologane (230690939)
Date: 25 May 2025
*/
/*package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Admin;
import za.ac.cput.repository.AdminRepository;

import java.util.List;


@Service
public class AdminService implements IAdminService {
    private static IAdminService service;

    AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin read(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        System.out.println("Updating Admin " + admin);
        return adminRepository.save(admin);

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
        return this.adminRepository.findAll();
    }

}
*/