package com.adepuu.montrackbackend.auth.service.impl;

import com.adepuu.montrackbackend.auth.repository.AuthRedisRepository;
import com.adepuu.montrackbackend.auth.service.AuthService;
import com.adepuu.montrackbackend.users.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Log
@Service
public class AuthServiceImpl implements AuthService {
  private final JwtEncoder jwtEncoder;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final AuthRedisRepository authRedisRepository;

  public AuthServiceImpl(JwtEncoder jwtEncoder, PasswordEncoder passwordEncoder, UserRepository userRepository, AuthRedisRepository authRedisRepository) {
    this.jwtEncoder = jwtEncoder;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.authRedisRepository = authRedisRepository;
  }

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    String scope = authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

    var existingKey = authRedisRepository.getJwtKey(authentication.getName());
    if (existingKey != null) {
      log.info("Token already exists for user: " + authentication.getName());
      return existingKey;
    }

    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .subject(authentication.getName())
            .claim("scope", scope)
            .claim("userId", userRepository.findByEmail(authentication.getName()).get().getId())
            .build();

    var jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    authRedisRepository.saveJwtKey(authentication.getName(), jwt);
    return jwt;
  }
}