package com.example.demo8.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {
  @GetMapping("/")
  public String homeGet() {
    return "home";
  }

  @GetMapping("/guest/guestList")
  public String guestListGet(Model model, String mid) {
    log.info("방명록입니다.");
    model.addAttribute("mid", mid);
    return "guest/guestList";
  }
  @GetMapping("/board/boardList")
  public String boardListGet() {
    log.info("게시판입니다.");
    return "board/boardList";
  }
  @GetMapping("/pds/pdsList")
  public String pdsListGet() {
    log.info("자료실입니다.");
    return "pds/pdsList";
  }
}
