package com.dot.tour_info_service_server.dto.request.follow;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowRequestDTO {
  @NotEmpty(message = "mno cannot be Empty")
  private Long memberMno;
  @NotEmpty(message = "mno cannot be Empty")
  private Long followerMno;
}
