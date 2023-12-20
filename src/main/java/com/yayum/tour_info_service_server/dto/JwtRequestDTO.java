package com.yayum.tour_info_service_server.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtRequestDTO {
    private String email;
    private String password;
}
