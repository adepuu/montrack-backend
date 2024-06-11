package com.adepuu.montrackbackend.users.dto;

import com.adepuu.montrackbackend.users.entity.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    public Users toEntity() {
        return Users.builder()
                .username(name)
                .email(email)
                .build();
    }
}
