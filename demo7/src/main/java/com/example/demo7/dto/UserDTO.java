package com.example.demo7.dto;

import com.example.demo7.entity.User;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
  private long id;
  private String mid;
  private String pwd;
  private String name;
  private int age;
  private String gender;
  private String address;

  public static UserDTO entityToDTO(Optional<User> user) {
    return UserDTO.builder()
            .id(user.get().getId())
            .mid(user.get().getMid())
            .pwd(user.get().getPwd())
            .name(user.get().getName())
            .age(user.get().getAge())
            .gender(user.get().getGender())
            .address(user.get().getAddress())
            .build();
  }
}
