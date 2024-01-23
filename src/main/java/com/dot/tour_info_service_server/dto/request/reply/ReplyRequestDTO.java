package com.dot.tour_info_service_server.dto.request.reply;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyRequestDTO {
  @NotNull
  private Long rno;
  private Long parentRno;
  @NotNull
  private String text;
  @NotNull
  private Long mno;
  @NotNull
  private Long bno;
  private LocalDateTime regDate, modDate;

}
