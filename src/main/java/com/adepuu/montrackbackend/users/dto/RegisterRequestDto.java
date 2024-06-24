package com.adepuu.montrackbackend.users.dto;

import com.adepuu.montrackbackend.users.entity.Users;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequestDto implements Serializable {
  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Email is required")
  private String email;

  @NotBlank(message = "Password is required")
  private String password;

  @NotNull
  @Min(1)
  private int activeCurrency;

  public Users toEntity() {
    Users user = new Users();
    user.setDisplayName(name);
    user.setEmail(email);
    user.setPassword(password);
    return user;
  }
}
