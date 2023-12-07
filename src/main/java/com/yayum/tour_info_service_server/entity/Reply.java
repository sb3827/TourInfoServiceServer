package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Reply extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rno;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member mno;
  @ManyToOne(fetch = FetchType.LAZY)
  private Board bno;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_rno")
  private Reply parent;   // 원래 댓글

  @Column(nullable = false, length =1000)
  private String text;    // 댓글 내용
  }
