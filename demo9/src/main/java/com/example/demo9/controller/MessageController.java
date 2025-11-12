package com.example.demo9.controller;

import com.example.demo9.dto.PageDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller()
public class MessageController {
	@RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
	public String MessageGet(Model model, HttpSession session, PageDTO pageDTO,
                           @PathVariable String msgFlag,
                           @RequestParam(name="mid", defaultValue = "", required = false) String mid,
                           @RequestParam(name="idx", defaultValue = "0", required = false) int idx,
                           @RequestParam(name="tempFlag", defaultValue = "", required = false) String tempFlag) throws UnsupportedEncodingException {
		if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("message", "방명록에 글이 등록되었습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("message", "글이 입력되었습니다.");
			model.addAttribute("url", "/board/boardList");
		}

		return "include/message";
	}
}
