package com.example.demo4.controller;

import com.example.demo4.common.SecurityUtil;
import com.example.demo4.dto.ExamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
//@RestController
public class HomeController {
  @Autowired
  com.example.demo4.service.HomeService homeService;

  @GetMapping("/")
  public String indexGet() {
    return "index";
  }

  @GetMapping("/exam/hello")
  public String helloGet(Model model,
                         @RequestParam(name = "mid", defaultValue = "", required = false)String mid,
                         @RequestParam(name = "pwd", defaultValue = "", required = false)String pwd) {
    System.out.println(mid);
    System.out.println(pwd);

    model.addAttribute("mid", mid);
    model.addAttribute("pwd", pwd);
    return "exam/hello";
  }

  @GetMapping("/exam/ex1/{message}")
  public String ex1Get(Model model,
                       @PathVariable String message) {
    model.addAttribute("message", message);
    return "exam/ex1";
  }
  @GetMapping("/exam/ex2")
  public String ex2Get(Model model, String mid) {
    model.addAttribute("mid", mid);
    return "exam/ex2";
  }
  @GetMapping("/exam/ex3")
  public String ex3Get() {
    return "exam/ex3";
  }
  @PostMapping("/exam/ex3")
  public String ex3Post(Model model, ExamDTO dto) {
    System.out.println("dto1: "+dto);
    SecurityUtil securityUtil = new SecurityUtil();
    String salt = UUID.randomUUID().toString().substring(0,4);
    dto.setPwd(securityUtil.encryptSHA256(salt+dto.getPwd()));
    System.out.println("dto2: "+dto);
    model.addAttribute("dto", dto);
    return "exam/ex3Res";
  }
  @GetMapping("/exam/ex4")
  public String ex4Get(Model model, ExamDTO dto) {
    List<ExamDTO> examList = new ArrayList<>();
    for(int i=0; i<10; i++) {
      dto = new ExamDTO();
      dto.setName("user"+(i+1));
      dto.setAge((int)(Math.random()*(99-10+1)+10));

      examList.add(dto);
    }

    Map<String, ExamDTO> map = new HashMap<>();
    map.put("user1", new ExamDTO("홍길동", 11));
    map.put("user2", new ExamDTO("이기자", 22));
    map.put("user3", new ExamDTO("김말숙", 33));
    map.put("user4", new ExamDTO("오하늘", 44));
    map.put("user5", new ExamDTO("소나무", 55));
    map.put("user6", new ExamDTO("김연아", 66));

    model.addAttribute("message1", "안녕하세요.");
    model.addAttribute("message2", "<font color='red'><b>안녕하세요</b></font>.");
    model.addAttribute("examList", examList);
    model.addAttribute("examMap", map);
    return "exam/ex4";
  }

}
