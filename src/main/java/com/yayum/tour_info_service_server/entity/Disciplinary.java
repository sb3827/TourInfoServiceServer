
package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Member;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@ToString
public class Disciplinary {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long dno;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member mno;

  private String reason;
  private LocalDate strDate;  // 제재 시작 일자
  private LocalDate expDate;  // 제재 종료 일자
}