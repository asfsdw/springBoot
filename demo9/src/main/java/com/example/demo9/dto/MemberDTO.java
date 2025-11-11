package com.example.demo9.dto;

import com.example.demo9.constant.Role;
import com.example.demo9.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
  private Long id;
  @NotEmpty(message = "이메일을 입력해주세요.")
  @Email(message = "이메일 형식으로 입력해주세요.")
  private String email;
  @NotEmpty(message = "비밀번호를 입력해주세요.")
  @Length(min = 4, max = 255, message = "비밀번호는 최소 4글자 이상 입력해주세요.")
  private String password;
  @NotEmpty(message = "이름을 입력해주세요.")
  @Length(min = 2, max = 20, message = "이름은 최소 2글자 이상 입력해주세요.")
  private String name;
  private String address;
  private Role role;

  public static MemberDTO entityToDTO(Optional<Member> member) {
    return MemberDTO.builder()
            .id(member.get().getId())
            .email(member.get().getEmail())
            .password(member.get().getPassword())
            .name(member.get().getName())
            .address(member.get().getAddress())
            .role(member.get().getRole())
            .build();
  }
}
