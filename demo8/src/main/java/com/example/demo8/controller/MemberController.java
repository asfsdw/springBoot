package com.example.demo8.controller;

import com.example.demo8.dto.MemberFormDTO;
import com.example.demo8.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/memberLogin")
  public String memberLoginGet() {
    return "member/memberLogin";
  }
  @GetMapping("/memberMain")
  public String memberMainGet() {
    return "member/memberMain";
  }
  @GetMapping("/login/error")
  public String errorGet(RedirectAttributes rttr) {
    rttr.addFlashAttribute("loginErrorMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
    return "redirect:/member/memberLogin";
  }

  @GetMapping("/memberJoin")
  public String memberJoinGet(Model model) {
    model.addAttribute("dto", new MemberFormDTO());
    return "member/memberJoin";
  }
  @PostMapping("/memberJoin")
  public String memberJoinPost(Model model, RedirectAttributes rttr,
                               @Valid @ModelAttribute("dto") MemberFormDTO dto,
                               BindingResult bindingResult) {
    if(bindingResult.hasErrors()) return "member/memberJoin";
    memberService.setMember(rttr, dto);

    return "redirect:/";
  }

  @GetMapping("/memberLogout")
  public String memberLogoutGet(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication != null) new SecurityContextLogoutHandler().logout(request, response, authentication);
    rttr.addFlashAttribute("message", "로그아웃되었습니다.");
    return "redirect:/";
  }
}
