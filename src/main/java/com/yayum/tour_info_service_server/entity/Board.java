package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isAd;

    @Column(nullable = false)
    private Boolean isCourse;

    @ColumnDefault("0")
    private Double score;

    @ColumnDefault("0")
    private int likes;

    @ManyToOne(fetch= FetchType.LAZY)
    private Member mno;
}
