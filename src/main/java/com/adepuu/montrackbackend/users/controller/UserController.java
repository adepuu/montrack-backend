package com.adepuu.montrackbackend.users.controller;

import com.adepuu.montrackbackend.responses.Response;
import com.adepuu.montrackbackend.users.dto.RegisterRequestDto;
import com.adepuu.montrackbackend.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<Response<Object>> register(@Validated @RequestBody RegisterRequestDto registerRequestDto) {
    return Response.success("User registered successfully", userService.register(registerRequestDto));
  }

  @GetMapping("/profile")
  public ResponseEntity<Response<Object>> profile() {
    return Response.success("User profile", null);
  }
}
