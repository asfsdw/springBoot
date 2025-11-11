package com.example.demo6.entity;

import com.example.demo6.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long idx;
  @Column(nullable = false, length = 50)  // null사용 = 하지 않겠다
  private String itemName;
  private int price;
  private int stockNumber;
  @Lob  // text타입(@BLOB(binary), @CLOB(char))
  @Column(nullable = false)
  private String itemDetail;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ItemSellStatus itemSellStatus;
  @CreatedDate  // now()
  private LocalDateTime regTime;  // 상품등록 날짜시간
  @LastModifiedDate
  private LocalDateTime updateTime; // 상품수정 날짜시간
}
