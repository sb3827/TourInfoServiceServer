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
  @NotEmpty (message = "null title")
  private String title;
  private String content;
  private Double score;
  private Long writer;
  @NotEmpty (message = "null placeList")
  private List<List<Long>> coursePlaceList;
  private List<Long> images; // 등록 img number
  private List<String> deleteImages; // 삭제 대상 img src list
}
