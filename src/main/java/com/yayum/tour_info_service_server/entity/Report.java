package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.ibatis.annotations.Many;

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
    private Boolean isDone;
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member mno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board bno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reply rno;

}
