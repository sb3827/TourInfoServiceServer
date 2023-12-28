package com.yayum.tour_info_service_server.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardReplyCountDTO {
    private Long bno;
    private String title;
    private Long replyCount;
    private LocalDateTime regdate;

}
