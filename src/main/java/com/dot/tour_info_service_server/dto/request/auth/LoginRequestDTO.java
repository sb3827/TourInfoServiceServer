package com.dot.tour_info_service_server.dto.request.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequestDTO {
    private String email;
    private String password;
}
