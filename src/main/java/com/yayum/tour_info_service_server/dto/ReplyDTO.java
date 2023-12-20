package com.yayum.tour_info_service_server.dto;

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
  private String text;
  private Long mno;
  private String name;
  private Long bno;
  private LocalDateTime regDate, modDate;

}
