package com.dot.tour_info_service_server.dto.request.reply;

import jakarta.validation.constraints.NotBlank;
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
  private Long rno;
  private Long parentRno;
  @NotBlank(message = "text cannot be blank")
  private String text;
  private Long mno;
  private Long bno;
  private LocalDateTime regDate, modDate;
}
