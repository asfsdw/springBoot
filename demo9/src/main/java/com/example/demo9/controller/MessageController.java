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
                           @RequestParam(name="flag", defaultValue = "", required = false) String flag) throws UnsupportedEncodingException {
		if(msgFlag.equals("wrongAccess")) {
			model.addAttribute("message", "잘못된 접근입니다..");
			model.addAttribute("url", "/");
		}
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("message", "방명록에 글이 등록되었습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("message", "글이 입력되었습니다.");
			model.addAttribute("url", "/board/boardList");
		}
		else if(msgFlag.equals("passwordWrong")) {
			model.addAttribute("message", "비밀번호가 맞지 않습니다.");
			model.addAttribute("url", "/member/memberPasswordCheck?flag="+flag);
		}
    else if(msgFlag.equals("memberUpdateOk")) {
			model.addAttribute("message", "회원정보가 수정되었습니다.");
			model.addAttribute("url", "/member/memberMain");
		}
    else if(msgFlag.equals("memberUpdateNo")) {
			model.addAttribute("message", "회원정보 수정에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
			model.addAttribute("url", "/member/memberMain");
		}
    else if(msgFlag.equals("passwordUpdateNo")) {
			model.addAttribute("message", "비밀번호 변경에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
			model.addAttribute("url", "/member/memberMain");
		}
    else if(msgFlag.equals("passwordUpdateOk")) {
			model.addAttribute("message", "비밀번호가 변경되었습니다.\n다시 로그인해주세요.");
			model.addAttribute("url", "/member/memberLogout");
		}
    else if(msgFlag.equals("memberDeleteNo")) {
			model.addAttribute("message", "회원탈퇴에 실패했습니다.\n잠시 후, 다시 시도해주세요.");
			model.addAttribute("url", "/member/memberMain");
		}
    else if(msgFlag.equals("memberDeleteOk")) {
			model.addAttribute("message", "회원탈퇴되었습니다.");
			model.addAttribute("url", "/member/memberLogout");
		}
    else if(msgFlag.equals("fileUploadNo")) {
			model.addAttribute("message", "파일 업로드에 실패했습니다.");
			model.addAttribute("url", "/pds/pdsList");
		}
    else if(msgFlag.equals("fileUploadOk")) {
			model.addAttribute("message", "파일이 업로드되었습니다.");
			model.addAttribute("url", "/pds/pdsList");
		}
		return "include/message";
	}
}
