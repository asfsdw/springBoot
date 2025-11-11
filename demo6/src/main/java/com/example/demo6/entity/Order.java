package com.example.demo6.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user4_id")
  private User4 user4;
  @CreatedDate
  private LocalDateTime orderDate;
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
  private List<OrderItem> orderItem = new ArrayList<>();
}
