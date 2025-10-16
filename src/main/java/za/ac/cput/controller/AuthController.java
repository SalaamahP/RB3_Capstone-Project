package za.ac.cput.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import za.ac.cput.controller.dto.LoginRequestDTO;
import za.ac.cput.controller.dto.LoginResponseDTO;
import za.ac.cput.controller.dto.RegisterRequestDTO;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.service.RoleService;
import za.ac.cput.service.UserService;

import java.beans.Encoder;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private UserService userService;
  private RoleService roleService;

  @Autowired
  public AuthController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
    User user = userService.findByEmailOrStudentOrStaff(loginRequestDTO.getUserId());
    if (user == null || !user.getPassword().equals(loginRequestDTO.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }
    List<String> roleNames = user.getUserRoles()
            .stream()
            .map(ur -> ur.getRole().getRoleName())
            .collect(Collectors.toList());

    LoginResponseDTO response = new LoginResponseDTO(
            user.getUserId(),
            user.getName(),
            user.getEmail(),
            roleNames
    );
    return ResponseEntity.ok(response);

  }

  @PostMapping("/register")

public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) {


  if (userService.findByEmailOrStudentOrStaff(registerRequestDTO.getEmail()) != null
          || (registerRequestDTO.getStudentNumber() != null &&
          userService.findByEmailOrStudentOrStaff(registerRequestDTO.getStudentNumber()) != null)
          || (registerRequestDTO.getStaffNumber() != null &&
          userService.findByEmailOrStudentOrStaff(registerRequestDTO.getStaffNumber()) != null)) {
    throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
  }

  User newUser;
  try {
    newUser = UserFactory.createUser(
            registerRequestDTO.getName(),
            registerRequestDTO.getSurname(),
            registerRequestDTO.getEmail(),
            registerRequestDTO.getPassword(),
            registerRequestDTO.getPhone(),
            registerRequestDTO.getStudentNumber(),
            registerRequestDTO.getStaffNumber()
    );
  }catch (IllegalArgumentException e) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
  }

newUser = userService.create(newUser);

  Role defaultRole = roleService.findByRoleName("STUDENT");
//    if (registerRequestDTO.getStudentNumber() != null && !registerRequestDTO.getStudentNumber().isEmpty()) {
//    defaultRole = roleService.findByRoleName("STUDENT");
//    }else if (registerRequestDTO.getStudentNumber() != null && !registerRequestDTO.getStaffNumber().isEmpty()) {
//      defaultRole = roleService.findByRoleName("ADMIN");
//
//    }else{
//      defaultRole = roleService.findByRoleName("ORGANIZER");
//
//  }
  if (defaultRole != null) {
   newUser.addRole(defaultRole);
    userService.update(newUser);
  }

  List<String> roleNames = newUser.getUserRoles()
          .stream()
          .map(ur -> ur.getRole().getRoleName())
          .collect(Collectors.toList());

  LoginResponseDTO response = new LoginResponseDTO(
          newUser.getUserId(),
          newUser.getName(),
          newUser.getEmail(),
          roleNames
  );


  return ResponseEntity.ok(response);
}
}