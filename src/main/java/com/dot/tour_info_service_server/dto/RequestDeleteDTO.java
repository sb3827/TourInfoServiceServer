package com.dot.tour_info_service_server.dto;

import jakarta.transaction.Transactional;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeleteDTO {
    List<String> srcs;
}
