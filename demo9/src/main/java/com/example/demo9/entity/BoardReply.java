package com.example.demo9.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_reply1")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class BoardReply {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reply_id")
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id", referencedColumnName = "board_id", nullable = false)
  @ToString.Exclude
  private Board board;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
  @ToString.Exclude
  private Member member;
  @Column(nullable = false, length = 20)
  private String name;
  @Lob
  @NotEmpty
  private String content;
  @Column(nullable = false, length = 40)
  private String hostIP;
  @CreatedDate
  private LocalDateTime wDate;
}
