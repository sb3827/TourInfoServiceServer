package com.yayum.tour_info_service_server.entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RolePK implements Serializable {

  private int role;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member mno;

}
