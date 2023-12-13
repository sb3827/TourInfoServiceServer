package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.ibatis.annotations.Many;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Report extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;

    @ColumnDefault("false")
    @Column(nullable = false)
    private Boolean isDone;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(nullable = false)
    private String message;

    private Long complainant_mno;

    private Long defendant_mno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reply reply;

    public void changeIsDone(Boolean isDone){this.isDone=isDone;};

}
