package com.dot.tour_info_service_server.dto.response.follow;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowResponseDTO {
  @NotNull
  private Long mno;
  @NotNull
  private String name;
  private String image;
}
