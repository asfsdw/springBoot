package com.example.demo7.controller;

import com.example.demo7.common.SecurityUtil;
import com.example.demo7.dto.UserDTO;
import com.example.demo7.entity.User;
import com.example.demo7.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  @GetMapping("/userInput")
  public String userInputGet() {
    return "user/userInput";
  }
  @PostMapping("/userInput")
  public String userInputPost(RedirectAttributes rttr, UserDTO dto) {
    String mid = dto.getMid();
    Optional<User> user = userService.getUserMidSearch(dto.getMid());
    if(user.isPresent()) {
      rttr.addFlashAttribute("message", "중복된 아이디가 있습니다.");
      return "redirect:/user/userInput";
    }

    SecurityUtil securityUtil = new SecurityUtil();
    String salt = UUID.randomUUID().toString().substring(0, 2);
    dto.setPwd(securityUtil.encryptSHA256(salt+dto.getPwd())+salt);
    userService.setUser(dto);
    rttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");
    return "redirect:/user/userInput";
  }
  @GetMapping("/userList")
  public String userListGet(Model model,
                            @RequestParam(name = "flag", defaultValue = "", required = false)String flag) {
    if(flag.isEmpty()) {
      List<User> userList = userService.getUserList();
      model.addAttribute("userList", userList);
    }
    else if(flag.equals("desc")) {
      List<User> userList = userService.getUserListDesc();
      model.addAttribute("userList", userList);
    }
    return "user/userList";
  }
  @PostMapping("/userIdCheck")
  public String userIdCheckPost(Model model, RedirectAttributes rttr, String mid, String searchFlag) {
    if(searchFlag.equals("s")) {
      List<User> userList = userService.getUserMidLike(mid);
      model.addAttribute("userList", userList);
      return "user/userList";
    }
    else {
      Optional<User> user = userService.getUserMidSearch(mid);
      if(user.isEmpty()) {
        rttr.addFlashAttribute("message", "회원이 존재하지 않습니다.");
        return "redirect:/";
      }

      UserDTO dto = UserDTO.entityToDTO(user);

      model.addAttribute("dto", dto);
      return "user/userUpdate";
    }
  }
  @PostMapping("/userUpdate")
  public String userUpdatePost(RedirectAttributes rttr, UserDTO dto) {
    userService.setUserUpdate(dto);
    rttr.addFlashAttribute("message", "회원정보가 수정되었습니다.");
    return "redirect:/user/userList";
  }
  @ResponseBody
  @PostMapping("/userDelete")
  public int userDeletePost(long id) {
    userService.setUserDelete(id);
    int res = 0;
    Optional<User> user = userService.getUserId(id);
    if(user.isEmpty()) res = 1;
    return res;
  }
  @GetMapping("/userLogout")
  public String userLogoutGet(HttpSession session, RedirectAttributes rttr) {
    session.invalidate();
    rttr.addFlashAttribute("message", "로그아웃되었습니다.");
    return "redirect:/";
  }
}
