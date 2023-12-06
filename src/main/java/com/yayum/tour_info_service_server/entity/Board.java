package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Boolean isAd;
    private Boolean isCourse;
    private Double score;
    private int likes;

    @ManyToOne(fetch= FetchType.LAZY)
    private Member mno;
}
