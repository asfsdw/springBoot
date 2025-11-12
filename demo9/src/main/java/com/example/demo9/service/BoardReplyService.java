package com.example.demo9.service;

import com.example.demo9.entity.Board;
import com.example.demo9.entity.BoardReply;
import com.example.demo9.entity.Member;
import com.example.demo9.repository.BoardReplyRepository;
import com.example.demo9.repository.BoardRepository;
import com.example.demo9.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardReplyService {
  private final BoardReplyRepository boardReplyRepository;
  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;

  public List<BoardReply> getBoardReply(Long id) {
    return boardReplyRepository.findByBoardIdOrderById(id);
  }

  public void setBoardReply(BoardReply boardReply, Long parentId, String email, String hostIP) {
    Board board = boardRepository.findById(parentId).orElse(null);
    Member member = memberRepository.findByEmail(email).orElse(null);
    boardReplyRepository.save(BoardReply.builder()
                    .board(board)
                    .member(member)
                    .name(member.getName())
                    .content(boardReply.getContent())
                    .hostIP(hostIP)
                    .build());
  }

  public void setBoardReplyDelete(Long id) {
    boardReplyRepository.deleteById(id);
  }

  public void setBoardReplyUpdate(Long id, String content) {
    BoardReply boardReply = boardReplyRepository.findById(id).orElse(null);
    boardReply.setContent(content);
    boardReplyRepository.save(boardReply);
  }
}
