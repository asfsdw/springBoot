package com.example.demo9.controller;

import com.example.demo9.dto.BoardDTO;
import com.example.demo9.entity.Board;
import com.example.demo9.entity.Member;
import com.example.demo9.service.BoardService;
import com.example.demo9.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
  private final BoardService boardService;
  private final MemberService memberService;

  @GetMapping("boardList")
  public String boardListGet(Model model,
                             @RequestParam(name = "pag", defaultValue = "0", required = false)int pag,
                             @RequestParam(name = "pageSize", defaultValue = "10", required = false)int pageSize) {
    Page<Board> boardList = boardService.getBoardList(pag, pageSize);

    model.addAttribute("boardList", boardList);

    model.addAttribute("pag", pag);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("totPage", boardList.getTotalPages());
    model.addAttribute("curScrStartNo", (int)boardList.getTotalElements()-(pag*pageSize));
    model.addAttribute("blockSize", 3);
    model.addAttribute("curBlock", pag / 3);
    model.addAttribute("lastBlock", (boardList.getTotalPages()-1)/3);
    return "board/boardList";
  }

  @GetMapping("boardInput")
  public String boardInputGet() {
    return "board/boardInput";
  }
  @PostMapping("boardInput")
  public String boardInputPost(HttpServletRequest request, Authentication authentication, BoardDTO dto) {
    try {
      dto.setHostIP(request.getRemoteAddr());
      String email = authentication.getName();

      Member member = memberService.getMemberEmail(email).get();
      boardService.setBoard(dto, member);
    } catch (Exception e) {
      throw new IllegalStateException("잘못된 접근입니다.");
    }
    return "redirect:/message/boardInputOk";
  }
}
