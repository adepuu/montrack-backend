package com.adepuu.montrackbackend.auth.service.impl;

import com.adepuu.montrackbackend.auth.entity.UserAuth;
import com.adepuu.montrackbackend.users.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    var userData = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return new UserAuth(userData);
  }
}