package com.dot.tour_info_service_server.dto.response.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyMemberResponseDTO {
  private Long rno;
  private String text;
  private Long parent_rno;
  private LocalDateTime regDate;
  private Long mno;
  private String name;
  private String src;
}
