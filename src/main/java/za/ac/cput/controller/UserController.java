package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import za.ac.cput.controller.dto.UserDTO;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.service.RoleService;
import za.ac.cput.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    UserController(UserService service, RoleService roleService) {
        this.userService = service;
        this.roleService = roleService;
    }

    @PostMapping("/{userId}/addRole/{roleId}")
    public ResponseEntity<UserDTO>  addRole(@PathVariable Long userId, @PathVariable Long roleId) {
        User user = userService.read(userId);
        Role role = roleService.read(roleId);

        if (user == null || role == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or Role not found");    }

            user.addRole(role);
        List<String> roles = user.getUserRoles().stream()
                .map(ur-> ur.getRole().getRoleName())
                .collect(Collectors.toList());

        UserDTO userDTO = new UserDTO(user.getUserId(), user.getName(), user.getEmail(), roles);

        return ResponseEntity.ok(userDTO);






    }

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/read/{id}")
    public User read(@PathVariable Long id) {
        return userService.read(id);
    }

    @PutMapping("/update")
    public User update(@RequestBody User user) {
        User existingUser = userService.read(user.getUserId());
        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found" + user.getUserId());
        }

        User updatedUser = new User.Builder()
                .copy(existingUser)
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setPhone(user.getPhone())
                .build();
        return userService.update(updatedUser);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id) {
        boolean deleted = userService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/removeRole/{roleId}")
    public ResponseEntity<UserDTO> removeRole(@PathVariable Long userId, @PathVariable Long roleId) {
        User user = userService.read(userId);
        Role role = roleService.read(roleId);

        if(user == null || role == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or Role not found");
        }

            user.getUserRoles().removeIf(ur -> ur.getRole().equals(role));
            userService.update(user);

            List<String> roles = user.getUserRoles().stream()
                    .map(ur-> ur.getRole().getRoleName())
                    .collect(Collectors.toList());

            UserDTO userDTO = new UserDTO(user.getUserId(), user.getName(), user.getEmail(), roles);

            return ResponseEntity.ok(userDTO);

    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

}







