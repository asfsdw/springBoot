package com.example.demo8.repository;

import com.example.demo8.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Member findByName(String name);

  Optional<Member> findByEmail(@NotEmpty(message = "이메일은 필수입력 입니다.")String email);
}
