package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT '폴더'")
    private String title;

    public void changeTitle(String title){
        this.title=title;
    }
}
