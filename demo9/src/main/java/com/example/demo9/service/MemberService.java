package com.example.demo9.service;

import com.example.demo9.constant.UserDel;
import com.example.demo9.dto.MemberDTO;
import com.example.demo9.entity.Member;
import com.example.demo9.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
  @Autowired
  PasswordEncoder passwordEncoder;

  private final MemberRepository memberRepository;

  public void setMember(MemberDTO dto) {
    Optional<Member> member = getMemberEmail(dto.getEmail());
    if(member.isPresent()) throw new IllegalStateException("이미 존재하는 회원입니다.");
    try {
      memberRepository.save(Member.dtoToEntity(passwordEncoder, dto));
    } catch (Exception e) {
      throw new IllegalStateException("회원가입에 실패했습니다.");
    }
  }

  public Optional<Member> getMemberEmail(String email) {
    return memberRepository.findByEmail(email);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Member> member = Optional.ofNullable(getMemberEmailAndUserDel(email, UserDel.NO)
            .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 이메일입니다."))); // SecurityConfig에 로그인 실패를 따로 적용해뒀기 때문에 실제로 사용하지는 않는다.
    return User.builder()
            .username(member.get().getEmail())
            .password(member.get().getPassword())
            .roles(member.get().getRole().toString())
            .build();
  }

  private Optional<Member> getMemberEmailAndUserDel(String email, UserDel userDel) {
    return memberRepository.findByEmailAndUserDel(email, userDel);
  }

  public List<Member> getMemberList() {
    return memberRepository.findAll();
  }

  public void setMemberUpdate(MemberDTO dto) {
    memberRepository.setMemberUpdate(dto);
  }

  public void setMemberPasswordUpdate(String email, String password) {
    Member member = memberRepository.findByEmail(email).orElse(null);
    memberRepository.save(Member.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .password(passwordEncoder.encode(password))
                    .name(member.getName())
                    .address(member.getAddress())
                    .role(member.getRole())
                    .build());
  }

  public void setMemberDelete(String email) {
    Member member = memberRepository.findByEmail(email).orElse(null);
    member.setUserDel(UserDel.OK);
    memberRepository.save(member);
  }
}
