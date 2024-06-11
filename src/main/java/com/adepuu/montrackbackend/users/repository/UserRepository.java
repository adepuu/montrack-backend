package com.adepuu.montrackbackend.users.repository;

import com.adepuu.montrackbackend.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
