/* AdminController.java
 * Admin Controller class
 * Author: Oratile Phologane (230690939)
 * Date: 25 May 2025
 */
package za.ac.cput.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Admin;
import za.ac.cput.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")

public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Endpoint to create a new admin
    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin createdAdmin = adminService.create(admin);
        return ResponseEntity.ok(createdAdmin);
    }

    // Endpoint to read an admin by ID
    @GetMapping("/read/{id}")
    public ResponseEntity<Admin> readAdmin(@PathVariable Long id) {
        Admin admin = adminService.read(id);
        return ResponseEntity.ok(admin);
    }

    // Endpoint to update an existing admin
    @PutMapping("/update")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin) {
        Admin updatedAdmin = adminService.update(admin);
        return ResponseEntity.ok(updatedAdmin);
    }

    // Endpoint to get all admins
    @GetMapping("/getall")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAll();
        return ResponseEntity.ok(admins);
    }
}
