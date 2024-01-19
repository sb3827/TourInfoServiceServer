package com.dot.tour_info_service_server.dto;

import com.dot.tour_info_service_server.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestModifyMemberDTO {
    private Long mno;
    private MultipartFile image;
    private String name;
    private String email;
    private String phone;
    private LocalDate birth;
    private Role role;
}