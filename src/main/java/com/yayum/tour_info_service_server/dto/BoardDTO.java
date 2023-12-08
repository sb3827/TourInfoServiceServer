package com.yayum.tour_info_service_server.dto;

import com.yayum.tour_info_service_server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  private Member writer;
}
