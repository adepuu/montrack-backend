package com.adepuu.montrackbackend.users.controller;

import com.adepuu.montrackbackend.responses.Response;
import com.adepuu.montrackbackend.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/profile")
  public ResponseEntity<Response<Object>> profile() {
    return Response.success("User profile", null);
  }
}
