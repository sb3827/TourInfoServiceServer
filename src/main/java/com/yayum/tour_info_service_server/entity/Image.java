package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board; //게시글번호 - 머지 후 import 해야함

    @Column(nullable = false)
    private String src; //이미지 경로
}
