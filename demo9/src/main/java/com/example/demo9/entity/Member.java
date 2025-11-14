package com.example.demo9.entity;

import com.example.demo9.constant.Role;
import com.example.demo9.constant.UserDel;
import com.example.demo9.dto.MemberDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member1")
@DynamicInsert
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
  @Column(unique = true, nullable = false, length = 50)
  private String email;
  @NotNull
  @Column(nullable = false)
  private String password;
  @Column(nullable = false, length = 20)
  private String name;
  @Column(length = 50)
  private String address;
  @Enumerated(EnumType.STRING)
  private Role role;

  @Enumerated(EnumType.STRING)
  @ColumnDefault("'NO'")
  @Column(nullable = false)
  private UserDel userDel;

  public static Member dtoToEntity(PasswordEncoder passwordEncoder, MemberDTO dto) {
    return Member.builder()
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .name(dto.getName())
            .address(dto.getAddress())
            .role(Role.USER)
            .userDel(UserDel.NO)
            .build();
  }
}
