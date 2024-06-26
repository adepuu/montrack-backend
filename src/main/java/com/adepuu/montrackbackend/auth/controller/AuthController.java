package com.adepuu.montrackbackend.auth.controller;

import com.adepuu.montrackbackend.auth.dto.LoginRequestDto;
import com.adepuu.montrackbackend.auth.dto.LoginResponseDto;
import com.adepuu.montrackbackend.auth.entity.UserAuth;
import com.adepuu.montrackbackend.auth.service.AuthService;
import com.adepuu.montrackbackend.responses.Response;
import jakarta.servlet.http.Cookie;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
@Log
public class AuthController {
  private final AuthService authService;
  private final AuthenticationManager authenticationManager;

  public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
    this.authService = authService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto userLogin) throws IllegalAccessException {
    log.info("User login request received for user: " + userLogin.getEmail());
    Authentication authentication =
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            userLogin.getEmail(),
                            userLogin.getPassword()));

    var ctx = SecurityContextHolder.getContext();
    ctx.setAuthentication(authentication);

    UserAuth userDetails = (UserAuth) authentication.getPrincipal();
    log.info("Token requested for user :" + userDetails.getUsername() + " with roles: " + userDetails.getAuthorities().toArray()[0]);
    String token = authService.generateToken(authentication);

    LoginResponseDto response = new LoginResponseDto();
    response.setMessage("User logged in successfully");
    response.setToken(token);

    Cookie cookie = new Cookie("sid", token);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Set-Cookie", cookie.getName() + "=" + cookie.getValue() + "; Path=/; HttpOnly");
    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
  }
}