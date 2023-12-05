package com.yayum.tour_info_service_server.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno; //장소번호

    private String name; //장소명
    private Double lng; // 경도
    private Double lat; // 위도
    private String roadAddress; //도로명 주소
    private String localAddress; // 주소
    private String engAddress; //영문 주소
    private int cart; //장바구니 담은 총 갯수

}
