package com.example.demo8.entity;

import com.example.demo8.constant.Role;
import com.example.demo8.dto.MemberFormDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member1")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;
  @Column(unique = true, length = 60, nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;
  @Column(length = 20, nullable = false)
  private String name;
  @Column(length = 50)
  private String address;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  public static Member dtoToEntity(PasswordEncoder passwordEncoder, MemberFormDTO dto) {
    return Member.builder()
            .id(dto.getId())
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .name(dto.getName())
            .address(dto.getAddress())
            .role(Role.USER)  // dto.getRole로 주지 않는 이유는 해커가 ADMIN값을 줄 수 있기 때문에 강제로 USER를 주는 것이다.
            .build();
  }
}
