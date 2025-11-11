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
public class User4 {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user4_id")
  private Long idx;
  @Column(length = 20)
  private String name;
  private int age;
}
