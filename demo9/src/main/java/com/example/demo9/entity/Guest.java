package com.example.demo9.entity;

import com.example.demo9.dto.GuestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "guest1")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Guest {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "guest_id")
  private Long id;
  @NotBlank
  @Column(nullable = false, length = 20)
  private String name;
  @Lob
  @NotBlank
  @Column(nullable = false)
  private String content;
  @Email
  private String email;
  @URL
  private String homePage;
  @CreatedDate
  private LocalDateTime visitDate;
  @Column(nullable = false)
  private String hostIP;

  public static Guest dtoToEntity(GuestDTO dto) {
    return Guest.builder()
            .name(dto.getName())
            .content(dto.getContent())
            .email(dto.getEmail())
            .homePage(dto.getHomePage())
            .visitDate(dto.getVisitDate())
            .hostIP(dto.getHostIP())
            .build();
  }
}
