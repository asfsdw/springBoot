package com.example.demo6.dto;

import com.example.demo6.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {
  private Long idx;
  private String itemName;
  private int price;
  private int stockNumber;
  private String itemDetail;
  private ItemSellStatus itemSellStatus;
  private LocalDateTime regTime;  // 상품등록 날짜시간
  private LocalDateTime updateTime; // 상품수정 날짜시간
}
