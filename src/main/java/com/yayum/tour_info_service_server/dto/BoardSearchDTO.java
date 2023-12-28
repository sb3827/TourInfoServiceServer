package com.yayum.tour_info_service_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchDTO {
    Long bno;
    String title;
    int likes;
    Double score;
    Long mno;
    LocalDateTime regDate;
}
