package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardLikePK implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    private Member mno; // 좋아요한 유저번호 - 머지 후 import 해야함

    @ManyToOne(fetch = FetchType.LAZY)
    private Board bno; // 좋아요한 게시글 - 머지 후 import 해야함
}
