package com.example.demo9.common;

import com.example.demo9.dto.PageDTO;
import com.example.demo9.entity.Board;
import com.example.demo9.entity.Member;
import com.example.demo9.repository.BoardRepository;
import com.example.demo9.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Pagination {
  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;

  public PageDTO pagination(PageDTO dto) {	// 각각의 변수로 받으면 초기값처리를 spring이 자동할수 있으나, 객체로 받으면 개별 문자/객체 자료에는 null이 들어오기에 따로 초기화 작업처리해야함.
    int pag = dto.getPag();
    int pageSize = dto.getPageSize() == 0 ? 10 : dto.getPageSize();
    String part = dto.getPart() == null ? "" : dto.getPart();

    int totRecCnt = 0, totPage = 0;

    PageRequest pageable = PageRequest.of(pag, pageSize, Sort.by("id").descending());

    if(dto.getSection().equals("board")) {
      Page<Board> page;

      if (dto.getSearch() != null && !dto.getSearch().isEmpty()) {
        if(dto.getSearch().equals("title")) page = boardRepository.findByTitleContaining(dto.getSearchStr(), pageable);
        else if(dto.getSearch().equals("name")) page = boardRepository.findByNameContaining(dto.getSearchStr(), pageable);
        else page = boardRepository.findByContentContaining(dto.getSearchStr(), pageable);
      }
      else page = boardRepository.findAll(pageable);

      List<Board> boardList = page.getContent();

      boardList.forEach((board) -> {
        board.setHourDiff(Duration.between(board.getWDate(), LocalDateTime.now()).toHours());
        board.setDateDiff(LocalDateTime.now().getDayOfMonth() - board.getWDate().getDayOfMonth());
        board.setReplyCnt(board.getBoardReply().size());
      });

      dto.setBoardList(boardList);

      totRecCnt = (int) page.getTotalElements();
      totPage = page.getTotalPages();
    }
    else if(dto.getSection().equals("member")) {
      Page<Member> page;
      if(dto.getSearch() != null) {
        if(dto.getSearch().equals("email")) page = memberRepository.findByEmailContaining(dto.getSearchStr(), pageable);
        else if(dto.getSearch().equals("name")) page = memberRepository.findByNameContaining(dto.getSearchStr(), pageable);
        else page = memberRepository.findAll(pageable);
      }
      else page = memberRepository.findAll(pageable);


      dto.setMemberList(page.getContent());

      totRecCnt = (int)page.getTotalElements();
      totPage = page.getTotalPages();
    }

    int startIndexNo = pag * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		
		int blockSize = 3;
    int curBlock = pag / blockSize;
    int lastBlock = (totPage - 1) / blockSize;
		dto.setPag(pag);
		dto.setPageSize(pageSize);
		dto.setTotRecCnt(totRecCnt);
		dto.setTotPage(totPage);
		dto.setStartIndexNo(startIndexNo);
		dto.setCurScrStartNo(curScrStartNo);
		dto.setBlockSize(blockSize);
		dto.setCurBlock(curBlock);
		dto.setLastBlock(lastBlock);

		if(dto.getSearch() != null) {
			if(dto.getSearch().equals("title")) dto.setSearchKr("글제목");
			else if(dto.getSearch().equals("name")) dto.setSearchKr("글쓴이");
			else if(dto.getSearch().equals("content")) dto.setSearchKr("글내용");
		}
		dto.setSearch(dto.getSearch());
		dto.setSearchStr(dto.getSearchStr());
		
		dto.setPart(part);
		dto.setFlag(dto.getFlag());
		
		return dto;
	}


}
