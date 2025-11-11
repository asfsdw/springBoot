package com.example.demo9.entity;

import com.example.demo9.dto.BoardDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board1")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_id")
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "email", referencedColumnName = "email")
  private Member member;
  @Column(nullable = false, length = 20)
  private String name;
  @Column(nullable = false, length = 100)
  private String title;
  @Lob
  @NotNull
  private String content;
  @Column(nullable = false, length = 40)
  private String hostIP;
  @ColumnDefault("'OK'")
  @Column(length = 2)
  private String openSW;
  @ColumnDefault("0")
  private int readNum;
  @CreatedDate
  private LocalDateTime wDate;
  @ColumnDefault("0")
  private int good;
  @ColumnDefault("'NO'")
  private String complaint;

  // 댓글 Entity와의 연관관계.
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
  private List<BoardReply> boardReply = new ArrayList<>();

  // 테이블에 만들어지지 않는 필드로 계산시에 사용하기 위한 필드 선언: @Transient
  @Transient
  private long dateDiff;
  @Transient
  private long hourDiff;
  @Transient
  private long replyCnt;

  public static Board dtoToEntity(BoardDTO dto, Member member) {
    return Board.builder()
            .member(member)
            .name(dto.getName())
            .title(dto.getTitle())
            .content(dto.getContent())
            .hostIP(dto.getHostIP())
            .openSW(dto.getOpenSW())
            .readNum(dto.getReadNum())
            .wDate(dto.getWDate())
            .good(dto.getGood())
            .complaint(dto.getComplaint())
            .build();
  }
}
