package com.example.demo6.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_id")
  private Long id;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user4_id")
  private User4 user4;
}
