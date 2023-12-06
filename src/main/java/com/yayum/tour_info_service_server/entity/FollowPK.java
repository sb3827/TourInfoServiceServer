package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Builder
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class FollowPK {

  @ManyToOne(fetch = FetchType.LAZY)
  private Member mno;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member follower;

}
