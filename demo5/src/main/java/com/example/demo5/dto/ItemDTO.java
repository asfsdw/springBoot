package com.example.demo5.dto;

import lombok.*;

//@Data
@Getter
@Setter
@ToString
@NoArgsConstructor  // 기본 생성자만 쓰겠다.
@AllArgsConstructor // 모든 인자를 쓰는 사용자지정 생성자를 쓰겠다.
@Builder            // 사용자지정 생성자를 쓰겠다.
public class ItemDTO {
  private int idx;
  private String name;
}
