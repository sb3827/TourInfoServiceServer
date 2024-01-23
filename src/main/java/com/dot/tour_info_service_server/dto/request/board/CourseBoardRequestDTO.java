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
public class CourseBoardRequestDTO {
  private Long bno;
  @NotEmpty (message = "title is null")
  private String title;
  private String content;
  private Double score;
  private Long writer;
  @NotEmpty (message = "placeList is null")
  private List<List<Long>> coursePlaceList;
  private List<Long> images;
  private List<String> deleteImages;
}
