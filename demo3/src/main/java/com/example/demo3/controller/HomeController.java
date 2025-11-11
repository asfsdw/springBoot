package com.example.demo3.controller;

import com.example.demo3.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class HomeController {
  @Autowired
  HomeService homeService;

  @GetMapping("/hello")
  public String helloGet() {
    return "HELLO 홍길동";
  }
}
