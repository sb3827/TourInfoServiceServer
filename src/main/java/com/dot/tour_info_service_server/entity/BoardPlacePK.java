package com.dot.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Getter
@Builder
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BoardPlacePK implements Serializable {
    private int day;

    private int orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
