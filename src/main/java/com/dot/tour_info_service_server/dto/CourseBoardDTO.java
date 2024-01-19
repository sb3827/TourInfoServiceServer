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
public class CourseBoardDTO {
  private Long bno;
  private String title;
  private String content;
  private Double score;
  private Long writer;
  private List<List<Long>> coursePlaceList;
  private List<Long> images; // 등록 img number
  private List<String> deleteImages; // 삭제 대상 img src list
}
