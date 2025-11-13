package com.example.demo9.controller;

import com.example.demo9.common.Pagination;
import com.example.demo9.dto.MemberDTO;
import com.example.demo9.entity.Member;
import com.example.demo9.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
  @Autowired
  PasswordEncoder passwordEncoder;

  private final MemberService memberService;
  private final Pagination pagination;

  @GetMapping("/memberLogin")
  public String memberLoginGet() {
    return "member/memberLogin";
  }

  @GetMapping("/memberJoin")
  public String memberJoinGet(Model model) {
    model.addAttribute("dto", new MemberDTO());
    return "member/memberJoin";
  }
  @PostMapping("/memberJoin")
  public String memberJoinPost(RedirectAttributes rttr,
                               @Valid @ModelAttribute("dto") MemberDTO dto,
                               BindingResult bindingResult) {
    if(bindingResult.hasErrors()) return "member/memberJoin";

    try {
      memberService.setMember(dto);
    } catch (IllegalStateException e) {
      rttr.addFlashAttribute("message", e.getMessage());
      return "redirect:/member/memberJoin";
    }

    rttr.addFlashAttribute("message", "회원가입되셨습니다.");
    return "redirect:/member/memberLogin";
  }

  @GetMapping("/memberLoginOk")
  public String memberMain(RedirectAttributes rttr,
                           HttpSession session,
                           Authentication authentication) {
    String email = authentication.getName();
    Optional<Member> member = memberService.getMemberEmail(email);
    String strLevel = member.get().getRole().toString();
    if(strLevel.equals("ADMIN")) strLevel = "관리자";
    else if(strLevel.equals("USER")) strLevel = "정회원";
    else if(strLevel.equals("OPERATOR")) strLevel = "운영자";

    session.setAttribute("sName", member.get().getName());
    session.setAttribute("sStrLevel", strLevel);
    rttr.addFlashAttribute("message", member.get().getName()+"님 로그인되셨습니다.");
    return "redirect:/member/memberMain";
  }
  @GetMapping("/login/error")
  public String error(RedirectAttributes rttr) {
    rttr.addFlashAttribute("loginErrorMsg", "<font color='red'>로그인에 실패했습니다.</font>");
    return "redirect:/member/memberLogin";
  }
  @GetMapping("/memberMain")
  public String memberMainGet() {
    return "member/memberMain";
  }

  @GetMapping("/memberLogout")
  public String memberLogout(RedirectAttributes rttr,
                             HttpServletRequest request, HttpServletResponse response, HttpSession session,
                             Authentication authentication) {
    try {
      if(authentication != null) {
        String name = session.getAttribute("sName").toString();
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        rttr.addFlashAttribute("message", name+"님 로그아웃되셨습니다.");
        return "redirect:/member/memberLogin";
      }
    } catch (Exception e) {
      rttr.addFlashAttribute("message", "잘못된 요청입니다.");
      return "redirect:/";
    }
    rttr.addFlashAttribute("message", "잘못된 요청입니다.");
    return "redirect:/";
  }

  @GetMapping("/memberPasswordCheck")
  public String memberUpdateGet(Model model, String flag) {
    model.addAttribute("flag", flag);
    return "member/memberPasswordCheck";
  }
  @PostMapping("/memberPasswordCheck")
  public String memberUpdatePost(Authentication authentication, Model model, RedirectAttributes rttr,
                                 String flag, String password) {
    if(!passwordEncoder.matches(password,memberService.getMemberEmail(authentication.getName()).orElse(null).getPassword())) {
      return "redirect:/message/passwordWrong?flag="+flag;
    }

    if(flag.equals("1")) {
      rttr.addAttribute("flag", flag);
      return "redirect:/member/memberUpdate";
    }
    else if(flag.equals("2")) {
      model.addAttribute("flag", "3");
      return "member/memberPasswordCheck";
    }
    return "redirect:/message/wrongAccess";
  }

  @GetMapping("/memberUpdate")
  public String memberUpdateGet(Authentication authentication, Model model, String flag) {
    model.addAttribute("dto", memberService.getMemberEmail(authentication.getName()).orElse(null));
    return "member/memberUpdate";
  }
  @PostMapping("/memberUpdate")
  public String memberUpdatePost(MemberDTO dto) {
    try {
      memberService.setMemberUpdate(dto);
    } catch (Exception e) {return "redirect:/message/memberUpdateNo";}
    return "redirect:/message/memberUpdateOk";
  }
  @ResponseBody
  @PostMapping("/memberValidate")
  public String memberValidate(String name) {
    if(name.isEmpty()) return "이름을 입력해주세요.";
    else if(name.length() < 2) return "이름을 최소 2글자 이상 입력해주세요.";
    else return "";
  }
  /*
  @ResponseBody
  @PostMapping("/memberValidate")
  public String memberValidate(@Valid MemberDTO dto, BindingResult bindingResult) {
    if(bindingResult.hasErrors()) return bindingResult.getFieldError().getDefaultMessage();
    else return "";
  }
  */

  @PostMapping("/memberPasswordUpdate")
  public String memberPasswordUpdate(Authentication authentication, String password) {
    try {
      memberService.setMemberPasswordUpdate(authentication.getName(), password);
    } catch (Exception e) {return "redirect:/message/passwordUpdateNo";}
   return "redirect:/message/passwordUpdateOk";
  }

  @GetMapping("/memberDelete")
  public String memberDeleteGet(Authentication authentication) {
    try {
      memberService.setMemberDelete(authentication.getName());
    } catch (Exception e) {return "redirect:/message/memberDeleteNo";}
    return "redirect:/message/memberDeleteOk";
  }
}
