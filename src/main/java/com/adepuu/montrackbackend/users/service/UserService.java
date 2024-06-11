package com.adepuu.montrackbackend.users.service;

import com.adepuu.montrackbackend.users.dto.RegisterRequestDto;
import com.adepuu.montrackbackend.users.entity.Users;

import java.util.List;

public interface UserService {
    Users register(RegisterRequestDto user);
    Users findByUsername(String username);
    Users findById(Long id);
    List<Users> findAll();
    void deleteById(Long id);
}
