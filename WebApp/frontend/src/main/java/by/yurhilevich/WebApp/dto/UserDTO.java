package by.yurhilevich.WebApp.dto;

import by.yurhilevich.WebApp.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String role;
}
