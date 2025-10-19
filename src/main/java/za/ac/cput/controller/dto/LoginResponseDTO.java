package za.ac.cput.controller.dto;

import java.util.List;

public class LoginResponseDTO {

    private Long userId;
    private String name;
    private String email;
    private List<RoleDTO> roles;

    public LoginResponseDTO(Long userId, String name, String email, List<RoleDTO> roles) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public record LoginResponse(Long userId, String name, String email, List<String> roles) {}
}
