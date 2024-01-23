package com.dot.tour_info_service_server.dto.request.board;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceBoardRequestDTO {
  private Long bno;
  @NotEmpty (message = "null title")
  private String title;
  private String content;
  private Double score;
  private Long writer;
  private Long place;
  private List<Long> images;
  private List<String> deleteImages;
}
