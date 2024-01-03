package com.dot.tour_info_service_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceDTO {
    private Long pno;
    private String name;
    private Double lng;
    private Double lat;
    private String roadAddress;
    private String localAddress;
    private String engAddress;
    private String category;
    private int cart;
    private LocalDateTime regDate, modDate;
}
