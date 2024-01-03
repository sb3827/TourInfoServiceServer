package com.dot.tour_info_service_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDTO {
    private Long complainant;
    private Long defendant;
    private Long bno;
    private Long rno;
    private String content;
    private String message;
}
