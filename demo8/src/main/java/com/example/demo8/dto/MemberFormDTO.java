package com.example.demo8.dto;

import com.example.demo8.constant.Role;
import com.example.demo8.entity.Member;
import jakarta.validation.constraints.NotBlank;
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
public class MemberFormDTO {
  private Long id;

  @NotBlank(message = "이름은 필수입력 입니다.")
  @Length(max = 20, message = "이름은 1~20글자까지 허용합니다.")
  private String name;

  @NotEmpty(message = "이메일은 필수입력 입니다.")
  private String email;

  @NotEmpty(message = "비밀번호는 필수입력 입니다.")
  @Length(min = 2, max = 20, message = "비밀번호는 2~20글자까지 허용합니다.")
  private String password;

  private String address;

  private Role role;

  public static MemberFormDTO entityToDto(Optional<Member> member) {
    MemberFormDTO dto = MemberFormDTO.builder()
            .id(member.get().getId())
            .name(member.get().getName())
            .email(member.get().getEmail())
            .password(member.get().getPassword())
            .address(member.get().getAddress())
            .role(member.get().getRole())
            .build();

    return dto;
  }

}
