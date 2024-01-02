package com.dot.tour_info_service_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {
  private Long rno;
  private Long parentRno;
  private String text;
  private Long mno;
  private Long bno;
  private LocalDateTime regDate, modDate;

}
