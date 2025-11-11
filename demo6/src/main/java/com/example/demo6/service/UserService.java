package com.example.demo6.service;

import com.example.demo6.entity.User4;
import com.example.demo6.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User4 getUserName(String name) {
    return userRepository.findByName(name);
  }

  public void setUser(User4 dto) {
    userRepository.save(dto);
  }

  public List<User4> UserList() {
    return userRepository.findAll();
  }
}
