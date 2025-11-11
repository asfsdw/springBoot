package com.example.demo8.repository;

import com.example.demo8.constant.Role;
import com.example.demo8.dto.MemberFormDTO;
import com.example.demo8.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
class MemberRepositoryTest {
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Test
  public void createMemberTest() {
    Member member = setMemberTest();
    String name = member.getName();
    System.out.println(member);
    memberRepository.save(member);
    Member dto = memberRepository.findByName(name);
    System.out.println(dto);
  }

  public Member setMemberTest() {
    MemberFormDTO dto = MemberFormDTO.builder()
            .email("hkd1234@green.com")
            .password("1234")
            .name("홍길동")
            .address("서울시")
            .role(Role.USER)
            .build();

    return Member.dtoToEntity(passwordEncoder, dto);
  }
}