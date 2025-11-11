package com.example.demo7.controller;

import com.example.demo7.common.SecurityUtil;
import com.example.demo7.entity.User;
import com.example.demo7.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {
  private final UserService userService;

  @GetMapping("/")
  public String homeGet() {
    return "home";
  }
  @PostMapping("/")
  public String homePost(HttpSession session, RedirectAttributes rttr, String mid, String pwd) {
    Optional<User> user = userService.getUserMidSearch(mid);
    if(user.isEmpty()) {
      rttr.addFlashAttribute("message", "존재하지 않는 아이디입니다.");
      return "redirect:/";
    }
    SecurityUtil securityUtil = new SecurityUtil();
    String salt = user.get().getPwd().substring(user.get().getPwd().length()-2);
    String tempPwd = securityUtil.encryptSHA256(salt+pwd);
    if(!user.get().getPwd().equals(tempPwd+salt)) {
      rttr.addFlashAttribute("message", "비밀번호가 틀렸습니다.");
      return "redirect:/";
    }

    session.setAttribute("sMid", user.get().getMid());
    return "redirect:/";
  }
}
