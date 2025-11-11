package com.example.demo9.dto;

import com.example.demo9.entity.Board;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
  private Long id;
  @NotEmpty(message = "이메일을 입력해주세요.")
  @Email(message = "이메일 형식을 맞춰주세요.")
  @Column(unique = true, nullable = false, length = 50)
  private String email;
  @NotEmpty(message = "이름을 입력해주세요.")
  @Column(nullable = false, length = 20)
  private String name;
  @NotEmpty(message = "글 제목을 입력해주세요.")
  @Column(nullable = false, length = 100)
  private String title;
  @NotEmpty(message = "글 내용을 입력해주세요.")
  private String content;
  private String hostIP;
  private String openSW;
  private int readNum;
  private LocalDateTime wDate;
  private int good;
  private String complaint;

  public static BoardDTO entityToDTO(Optional<Board> board) {
    return BoardDTO.builder()
            .id(board.get().getId())
            .email(board.get().getMember().getEmail())
            .name(board.get().getName())
            .title(board.get().getTitle())
            .content(board.get().getContent())
            .hostIP(board.get().getHostIP())
            .openSW(board.get().getOpenSW())
            .readNum(board.get().getReadNum())
            .wDate(board.get().getWDate())
            .good(board.get().getGood())
            .complaint(board.get().getComplaint())
            .build();
  }
}
