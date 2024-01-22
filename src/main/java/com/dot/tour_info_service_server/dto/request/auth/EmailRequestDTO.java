package com.dot.tour_info_service_server.dto.request.auth;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailRequestDTO {
    private String email;
}
