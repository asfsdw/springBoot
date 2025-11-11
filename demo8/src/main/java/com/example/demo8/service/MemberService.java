package com.example.demo8.service;

import com.example.demo8.dto.MemberFormDTO;
import com.example.demo8.entity.Member;
import com.example.demo8.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
  private final MemberRepository memberRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  public void setMember(RedirectAttributes rttr, MemberFormDTO dto) {
    try {
      log.info("========================통과11");
      Member member = memberRepository.save(Member.dtoToEntity(passwordEncoder, dto));
      log.info(member.toString());
      rttr.addFlashAttribute("message", "회원가입에 성공했습니다.");
    } catch (Exception e) {
      log.info("========================통과21");
      log.info("errorMessage: "+e.getMessage());
      rttr.addFlashAttribute("message", e.getMessage());
    }
    log.info("========================통과31");
  }

  // UserDetails(User)객체의 변환내용: 1.로그인아이디(username) 2.로그인비밀번호(password) 3.roles : role필드
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    /*
    Optional<Member> member = memberRepository.findByEmail(email);  // 쿼리 메소드를 사용한다.
    if(member.isPresent()) {
      Member findMember = member.get();
    }
    else {
      throw new UsernameNotFoundException(email + "사용자 없음");
    }
    return null;
    */

    // 위의 코드를 아래처럼 2줄로 줄여쓴다. 있으면 member객체를 반환, 없으면 메세지를 반환하도록 만들었다.
    Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 없습니다." + email));

    log.info("=============[로그인 사용자] : " + member);

    // return null;
    // 스프링 시큐리티에서 제공하는 User 객체를 만들어 반환
    return User.builder()
            .username(member.getEmail())
            .password(member.getPassword())
            .roles(member.getRole().toString())
            .build();
  }
}
