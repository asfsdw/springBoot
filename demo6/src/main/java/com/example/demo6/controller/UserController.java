package com.example.demo6.controller;

import com.example.demo6.entity.User4;
import com.example.demo6.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor  // 생성자 안 써도 괜찮게 해주는 어노테이션.
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  /*
  public UserController(UserService userService) {
    this.userService = userService;
  }
  */
  
  @GetMapping("/userList")
  public String userListGet(Model model,
                         @RequestParam(name = "searchStr", defaultValue = "", required = false)String searchStr) {
    if(searchStr.equals("")) {
      List<User4> dtos = userService.UserList();
      model.addAttribute("dtos", dtos);
    }
    else {
      User4 dtos = userService.getUserName(searchStr);
      model.addAttribute("dtos", dtos);
    }

    return "user/userList";
  }
  @GetMapping("/userInput")
  public String userInputGet() {
    return "user/userInput";
  }
  @PostMapping("/userInput")
  public String userInputPost(User4 dto) {
    userService.setUser(dto);
    return "redirect:/user/userList";
  }
  @ResponseBody
  @PostMapping("/nameCheck")
  public int nameCheckPost(User4 dto) {
    dto = userService.getUserName(dto.getName());
    if(dto == null) return 1;
    else return 0;
  }

}
