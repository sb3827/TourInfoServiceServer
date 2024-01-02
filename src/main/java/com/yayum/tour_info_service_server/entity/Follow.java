package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Builder
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
public class Follow{

  @EmbeddedId
  private FollowPK followPk;

}
