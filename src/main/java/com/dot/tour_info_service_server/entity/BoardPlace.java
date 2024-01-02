package com.dot.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class BoardPlace {
    @EmbeddedId
    private BoardPlacePK boardPlacePK;

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;
}
