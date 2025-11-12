package com.example.demo9.repository;

import com.example.demo9.entity.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {
  List<BoardReply> findByBoardIdOrderById(Long id);
}
