package com.example.demo9.dto;

import com.example.demo9.entity.Guest;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestDTO {
  private Long id;
  private String name;
  private String content;
  private String email;
  private String homePage;
  private LocalDateTime visitDate;
  private String hostIP;

  public static GuestDTO entityToDTO(Optional<Guest> guest) {
    return GuestDTO.builder()
            .id(guest.get().getId())
            .name(guest.get().getName())
            .content(guest.get().getContent())
            .email(guest.get().getEmail())
            .homePage(guest.get().getHomePage())
            .visitDate(guest.get().getVisitDate())
            .hostIP(guest.get().getHostIP())
            .build();
  }
}
