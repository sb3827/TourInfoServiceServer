package com.dot.tour_info_service_server.dto.request.auth;

import lombok.Data;

@Data
public class NewTokenRequestDTO {
    private String refreshToken;
}
