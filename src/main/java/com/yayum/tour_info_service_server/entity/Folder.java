package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member mno;

    private String title;

    public void changeTitle(String title){
        this.title=title;
    }
}
