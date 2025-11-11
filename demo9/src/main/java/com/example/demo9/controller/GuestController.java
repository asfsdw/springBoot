package com.example.demo9.controller;

import com.example.demo9.dto.GuestDTO;
import com.example.demo9.entity.Guest;
import com.example.demo9.service.GuestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestController {
	private final GuestService guestService;
	
	// 방명록 전체 보기.
	@GetMapping("/guestList")
	public String guestListGet(Model model,
			@RequestParam(name = "pag", defaultValue = "0", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
		Page<Guest> dtos = guestService.getGuestList(pag, pageSize);
		//int totRecCnt = guestService.getTotRecCnt("", "", "");
		int totPage = dtos.getTotalPages();
		int curScrStartNo = (int)dtos.getTotalElements() - (pag*pageSize);

		int blockSize = 3;
		int curBlock = (pag) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;

		model.addAttribute("guestList", dtos);

		model.addAttribute("pag", pag+1);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
    // 줄바꿈용 코드.
    model.addAttribute("newLine", System.lineSeparator());
		return "guest/guestList";
	}

	// 방명록 작성 폼 이동.
	@GetMapping("/guestInput")
	public String guestInputGet() {
		return "guest/guestInput";
	}
	// 방명록 작성.
	@PostMapping("/guestInput")
	public String guestInputPost(HttpServletRequest request, GuestDTO dto) {
    dto.setHostIP(request.getRemoteAddr());
		guestService.setGuest(dto);
		return "redirect:/message/guestInputOk";
	}

  /*
	// 관리자 인증 폼 보기.
	@GetMapping("Login")
	public String AdminGet() {
		return "guest/admin";
	}
	// 관리자 인증.
	@PostMapping("Login")
	public String AdminOkPost(HttpSession session, String mid, String pwd) {
		if(mid.equals("admin") && pwd.equals("1234")) {
			session.setAttribute("sAdmin", "adminOK");
			return "redirect:/Message/adminOk";
		}
		else return "redirect:/Message/adminNo";
	}
	*/

	// 방명록 삭제.
	@GetMapping("GuestDelete")
	public String GuestDeletePost(Long id) {
		guestService.setGuestDelete(id);
		return "redirect:/Message/guestDeleteOk";
	}
	/*
	// 로그아웃.
	@GetMapping("Logout")
	public String LogoutGet(HttpSession session) {
		session.removeAttribute("sAdmin");
		return "redirect:/Message/adminLogout";
	}

   */
}
