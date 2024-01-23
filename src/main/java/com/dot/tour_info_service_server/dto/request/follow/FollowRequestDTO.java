package com.dot.tour_info_service_server.dto.request.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowRequestDTO {
  private Long memberMno;
  private Long followerMno;
}
