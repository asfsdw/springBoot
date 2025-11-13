package com.example.demo9.service;

import com.example.demo9.dto.BoardDTO;
import com.example.demo9.entity.Board;
import com.example.demo9.entity.Member;
import com.example.demo9.repository.BoardRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;

  public Page<Board> getBoardList(int pag, int pageSize) {
    BooleanBuilder builder = new BooleanBuilder();
    PageRequest pageable = PageRequest.of(pag, pageSize, Sort.by("id").descending());
    return boardRepository.findAll(builder, pageable);
  }

  public void setBoard(BoardDTO dto, Member member) {
    try {
      boardRepository.save(Board.dtoToEntity(dto, member));
    }
    catch (Exception e) {
      throw new IllegalStateException("게시글 등록에 실패했습니다.");
    }
  }

  public Board getBoardId(Long id) {
    return boardRepository.findById(id).orElse(null);
  }

  public void setBoardReadNum(Long id) {
    boardRepository.setBoardReadNum(id);
  }

  public Board getPreNextSearch(Long id, String flag) {
    if(flag.equals("pre")) return boardRepository.findPrevious(id);
    else return boardRepository.findNext(id);
  }

  public void setBoardGood(String flag, Long id) {
    if(flag.equals("plus")) boardRepository.setBoardGood(id, 1);
    else boardRepository.setBoardGood(id, -1);
  }

  public void setBoardImageDelete(String realPath, String content) {
    int position = 21;
    String nextImg = content.substring(content.indexOf("src=\"/")+position);
    boolean sw = true;

    while(sw) {
      String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
      String oFilePath = realPath + imgFile;

      File delFile = new File(oFilePath);
      if(delFile.exists()) delFile.delete();

      if(nextImg.indexOf("src=\"/") == -1) sw = false;
      else nextImg = nextImg.substring(nextImg.indexOf("src=\"/")+position);
    }
  }

  public void setBoardDelete(Long id) {
    boardRepository.deleteById(id);
  }
}
