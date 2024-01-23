package com.dot.tour_info_service_server.dto.response.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyListResponseDTO {
  private Long rno;
  private Long mno;
  private Long bno;
  private String title;
  private String text;
  private LocalDateTime regdate;
  private boolean isCourse;
}
