package com.yayum.tour_info_service_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    Long sno;
    Long mno;
    Long bno;
    Long rno;
    Boolean isDone;
    String message;
    LocalDateTime regDate;
}
