package com.adepuu.montrackbackend.users.controller;

import com.adepuu.montrackbackend.responses.Response;
import com.adepuu.montrackbackend.users.dto.RegisterRequestDto;
import com.adepuu.montrackbackend.users.service.UserService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Log
public class UserController {
  private final UserService userService;

  public UserController(UserService userService, AuthenticationManager authenticationManager) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerRequestDto) {
    return Response.success("User registered successfully", userService.register(registerRequestDto));
  }

  @GetMapping("/profile")
  public ResponseEntity<?> profile() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    String username = authentication.getName();
    log.info("User profile requested for user: " + username);
    return Response.success("User profile", userService.findByEmail(username));
  }
}
