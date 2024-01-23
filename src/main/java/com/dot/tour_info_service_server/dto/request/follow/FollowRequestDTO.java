package com.dot.tour_info_service_server.dto.request.follow;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowRequestDTO {
  @NotNull
  private Long memberMno;
  @NotNull
  private Long followerMno;
}
