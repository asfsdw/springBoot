package com.example.demo7.service;

import com.example.demo7.dto.UserDTO;
import com.example.demo7.entity.User;
import com.example.demo7.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public Optional<User> getUserMidSearch(String mid) {
    return userRepository.findByMid(mid);
  }

  public void setUser(UserDTO dto) {
    userRepository.save(User.dtoToEntity(dto));
  }

  public List<User> getUserList() {
    return userRepository.findAll();
  }

  public List<User> getUserListDesc() {
    return userRepository.findByOrderByIdDesc();
  }

  public List<User> getUserMidLike(String mid) {
    return userRepository.findByMidContaining(mid);
  }

  public void setUserUpdate(UserDTO dto) {
    userRepository.save(User.dtoToEntity(dto));
  }

  public void setUserDelete(long id) {
    userRepository.deleteById(id);
  }

  public Optional<User> getUserId(long id) {
    return userRepository.findById(id);
  }
}
