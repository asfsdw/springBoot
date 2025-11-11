package com.example.demo7.entity;

import com.example.demo7.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user5")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user5_id")
  private long id;
  @Column(length = 20, nullable = false)
  private String mid;
  @Column(nullable = false)
  private String pwd;
  @Column(length = 5, nullable = false)
  private String name;
  @Column(nullable = false)
  private int age;
  @Column(length = 2, nullable = false)
  private String gender;
  @Column(length = 2)
  private String address;

  public static User dtoToEntity(UserDTO dto) {
    return User.builder()
            .id(dto.getId())
            .mid(dto.getMid())
            .pwd(dto.getPwd())
            .name(dto.getName())
            .age(dto.getAge())
            .gender(dto.getGender())
            .address(dto.getAddress())
            .build();
  }
}
