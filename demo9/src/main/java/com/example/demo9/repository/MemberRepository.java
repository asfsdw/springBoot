package com.example.demo9.repository;

import com.example.demo9.dto.MemberDTO;
import com.example.demo9.entity.Member;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByEmail(@NotEmpty(message = "이메일을 입력해주세요.") @Email(message = "이메일 형식으로 입력해주세요.") String email);

  Page<Member> findByEmailContaining(String searchStr, PageRequest pageable);

  Page<Member> findByNameContaining(String searchStr, PageRequest pageable);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("update Member set name = :#{#dto.name}, address = :#{#dto.address} where email = :#{#dto.email}")
  void setMemberUpdate(@Param("dto") MemberDTO dto);

  @Transactional
  @Modifying(clearAutomatically = true)
  void deleteByEmail(String email);
}
