package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.ibatis.annotations.Many;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;

    @ColumnDefault("false")
    private Boolean isDone;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member complainant;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reply reply;

}
