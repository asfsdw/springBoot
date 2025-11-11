package com.example.demo6.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User4DTO {
  private Long idx;
  private String name;
  private int age;
}
