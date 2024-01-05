package com.dot.tour_info_service_server.dto;

import com.dot.tour_info_service_server.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
  private Long bno;

  private String title;

  private String content;

  private Boolean isAd;

  private Boolean isCourse;

  private Double score;

  private int likes;

  private String writer;

  private LocalDateTime regDate, modDate;
}

