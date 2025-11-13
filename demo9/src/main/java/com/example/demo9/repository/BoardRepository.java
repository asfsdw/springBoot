package com.example.demo9.repository;

import com.example.demo9.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board> {
  Page<Board> findByTitleContaining(String search, PageRequest pageable);

  Page<Board> findByNameContaining(String search, PageRequest pageable);

  Page<Board> findByContentContaining(String search, PageRequest pageable);

  @Modifying(clearAutomatically = true)
  @Transactional
  @Query("update Board set readNum = readNum+1 where id = :id")
  void setBoardReadNum(Long id);

  @Query("select b from Board b where b.id < :id order by b.id desc limit 1")
  Board findPrevious(Long id);

  @Query("select b from Board b where b.id > :id order by b.id limit 1")
  Board findNext(Long id);

  @Modifying(clearAutomatically = true)
  @Transactional
  @Query("update Board set good = good + :good where id = :id")
  void setBoardGood(Long id, int good);
}
