package com.adepuu.montrackbackend.users.service.impl;

import com.adepuu.montrackbackend.users.dto.RegisterRequestDto;
import com.adepuu.montrackbackend.users.entity.Users;
import com.adepuu.montrackbackend.users.repository.UserRepository;
import com.adepuu.montrackbackend.users.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Users register(RegisterRequestDto user) {
    var newUser = user.toEntity();

    return userRepository.save(newUser);
  }

  @Override
  public Users findByUsername(String username) {
    return null;
  }

  @Override
  public Users findById(Long id) {
    return null;
  }

  @Override
  public List<Users> findAll() {
    return null;
  }

  @Override
  public void deleteById(Long id) {

  }
}
