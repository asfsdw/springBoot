package com.example.demo6.repository;

import com.example.demo6.entity.User4;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User4, Long> {
  User4 findByName(String name);
}
