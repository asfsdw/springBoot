package com.example.demo9.controller;

import com.example.demo9.common.Pagination;
import com.example.demo9.dto.BoardDTO;
import com.example.demo9.dto.PageDTO;
import com.example.demo9.entity.Board;
import com.example.demo9.entity.BoardReply;
import com.example.demo9.entity.Member;
import com.example.demo9.service.BoardReplyService;
import com.example.demo9.service.BoardService;
import com.example.demo9.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
  private final BoardService boardService;
  private final MemberService memberService;
  private final Pagination pagination;
  private final BoardReplyService boardReplyService;

  @GetMapping("boardList")
  public String boardListGet(Model model, PageDTO pageDTO) {
    pageDTO.setSection("board");
    pageDTO = pagination.pagination(pageDTO);

    model.addAttribute("boardList", pageDTO.getBoardList());
    model.addAttribute("pageDTO", pageDTO);

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

      Member member = memberService.getMemberEmail(email).orElse(null);
      boardService.setBoard(dto, member);
    } catch (Exception e) {
      throw new IllegalStateException("잘못된 접근입니다.");
    }
    return "redirect:/message/boardInputOk";
  }

  @GetMapping("/boardContent")
  private String boardContentGet(Model model, HttpSession session, Authentication authentication,
                                 BoardDTO dto, PageDTO pageDTO) {
    if(session.getAttribute("boardRead"+dto.getId()+session.getAttribute("sName")) == null) {
      boardService.setBoardReadNum(dto.getId());
      session.setAttribute("boardRead"+dto.getId()+session.getAttribute("sName"), "read");
    }

    Board board = boardService.getBoardId(dto.getId());

    // 이전글/다음글 가져오기.
    Board preDTO = boardService.getPreNextSearch(dto.getId(), "pre");
    Board nextDTO = boardService.getPreNextSearch(dto.getId(), "next");

    // 게시글 댓글 가져오기.
    List<BoardReply> replyList = boardReplyService.getBoardReply(dto.getId());

    // 본인이 쓴 게시글이면 isOwner를 true로.
    if(board.getMember().getEmail().equals(authentication.getName())) pageDTO.setOwner(true);

    model.addAttribute("board", board);
    model.addAttribute("newLine", System.lineSeparator());
    model.addAttribute("pageDTO", pageDTO);

    model.addAttribute("preDTO", preDTO);
    model.addAttribute("nextDTO", nextDTO);

    model.addAttribute("replyList", replyList);
    return "board/boardContent";
  }

  @ResponseBody
  @PostMapping("/boardReplyInput")
  public int boardReplyInputPost(Authentication authentication, HttpServletRequest request,
                                 BoardReply boardReply, Long parentId) {
    try {
      String email = authentication.getName();
      String hostIP = request.getRemoteAddr();
      boardReplyService.setBoardReply(boardReply, parentId, email, hostIP);
    } catch (Exception e) {return 0;}
    return 1;
  }
  @ResponseBody
  @PostMapping("/boardReplyDelete")
  public int boardReplyDeletePost(Long id){
    try {
      boardReplyService.setBoardReplyDelete(id);
    } catch (Exception e) {return 0;}
    return 1;
  }
  @ResponseBody
  @PostMapping("/boardReplyUpdate")
  public int boardReplyUpdatePost(Long id, String content) {
    try {
      boardReplyService.setBoardReplyUpdate(id, content);
    } catch (Exception e) {return 0;}
    return 1;
  }

  @ResponseBody
  @PostMapping("/boardGood")
  public int boardGoodPost(HttpSession session, Long id,
                           @RequestParam(name = "flag", defaultValue = "plus", required = false)String flag) {
    try {
      if(session.getAttribute("boardGood"+id+session.getAttribute("sName")) == null) {
        boardService.setBoardGood(flag, id);
        session.setAttribute("boardGood"+id+session.getAttribute("sName"), "good");
      }
    } catch (Exception e) {return 0;}
    return 1;
  }

  @ResponseBody
  @PostMapping("/boardDelete")
  public int boardDelete(HttpServletRequest request, Long id) {
    Board board = boardService.getBoardId(id);
    if(!board.getBoardReply().isEmpty()) return 2;

    try {
      String realPath = request.getServletContext().getRealPath("/ckeditorUpload/");
      if(board.getContent().contains("src=")) boardService.setBoardImageDelete(realPath, board.getContent());
      boardService.setBoardDelete(id);
    } catch (Exception e) {return 0;}
    return 1;
  }
}
