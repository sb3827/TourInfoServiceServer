package com.dot.tour_info_service_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {
    private String email;
    private String password;
    private LocalDate birth;
    private String phone;
    private String name;
    private String role;
    private int businessId;
}
