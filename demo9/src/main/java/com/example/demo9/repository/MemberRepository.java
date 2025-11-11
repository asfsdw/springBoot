package com.example.demo9.repository;

import com.example.demo9.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByEmail(@NotEmpty(message = "이메일을 입력해주세요.") @Email(message = "이메일 형식으로 입력해주세요.") String email);
}
