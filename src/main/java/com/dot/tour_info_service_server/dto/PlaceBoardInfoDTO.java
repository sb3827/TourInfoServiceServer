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
public class PlaceBoardInfoDTO {
  private String title;
  private String content;
  private Long mno;
  private String writer;
  private Boolean isCourse;
  private Boolean isAd;
  private int likes;
  private Double score;
  private LocalDateTime regdate;
  private LocalDateTime moddate;
  private PostingPlaceDTO[][] postingPlaceDTOS;
  private String[] images;
  private Boolean isLiked;
}

