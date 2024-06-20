package com.adepuu.montrackbackend.users.service.impl;

import com.adepuu.montrackbackend.auth.helpers.Claims;
import com.adepuu.montrackbackend.currency.entity.Currency;
import com.adepuu.montrackbackend.currency.repository.CurrencyRepo;
import com.adepuu.montrackbackend.exceptions.ApplicationException;
import com.adepuu.montrackbackend.exceptions.DataNotFoundException;
import com.adepuu.montrackbackend.users.dto.RegisterRequestDto;
import com.adepuu.montrackbackend.users.entity.Users;
import com.adepuu.montrackbackend.users.repository.UserRepository;
import com.adepuu.montrackbackend.users.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final CurrencyRepo currencyRepo;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CurrencyRepo currencyRepo) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.currencyRepo = currencyRepo;
  }

  @Override
  public Users register(RegisterRequestDto user) {
    Users newUser = user.toEntity();
    Currency currency = currencyRepo.findById(user.getActiveCurrency()).orElseThrow(() -> new DataNotFoundException("Currency not found"));
    currency.setId(user.getActiveCurrency());
    var password = passwordEncoder.encode(user.getPassword());
    newUser.setPassword(password);
    newUser.setCurrency(currency);
    return userRepository.save(newUser);
  }

  @Override
  public Users findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new ApplicationException("User not found"));
  }

  @Override
  public Users findById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new ApplicationException("User not found"));
  }

  @Override
  public List<Users> findAll() {
    return null;
  }

  @Override
  public void deleteById(Long id) {

  }

  @Override
  public Users profile() {
    var claims = Claims.getClaimsFromJwt();
    var email = (String) claims.get("sub");
    return findByEmail(email);
  }
}
