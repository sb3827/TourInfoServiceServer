package com.dot.tour_info_service_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceBoardDTO {
  private Long bno;

  private String title;

  private String content;

  private Boolean isAd;

  private Boolean isCourse;

  private Double score;

  private int likes;

  private Long writer;

  private LocalDateTime regDate, modDate;

  private Long pno;

  private List<String> srcList; //이미지 경로

}
