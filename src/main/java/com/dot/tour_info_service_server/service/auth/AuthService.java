package com.dot.tour_info_service_server.service.auth;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.dto.request.auth.EmailRequestDTO;
import com.dot.tour_info_service_server.dto.request.auth.LoginRequestDTO;
import com.dot.tour_info_service_server.dto.request.auth.SignupRequestDTO;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Role;

import java.util.Objects;

public interface AuthService {
    Long login(LoginRequestDTO requestDTO);
    Long signup(SignupRequestDTO signupDTO);
    Boolean emailCheck(String email);
    String findEmail(String name, String phone);
    ResponseDTO changePassword(ChangeMemberDTO changeMemberDTO);
    ResponseDTO resetPassword(EmailRequestDTO emailRequestDTO);
    Boolean checkValidate(String email);
    void resendEmail(String email);
    default Member signupDtoToEntity(SignupRequestDTO signupDTO) {
        Member member;

        if (Objects.equals(signupDTO.getRole(), "MEMBER")) {
            member = Member.builder()
                    .email(signupDTO.getEmail())
                    .password(signupDTO.getPassword())
                    .birth(signupDTO.getBirth())
                    .phone(signupDTO.getPhone())
                    .name(signupDTO.getName())
                    .isApprove(true)
                    .build();
            member.addMemberRole(Role.MEMBER);
        } else {
            member = Member.builder()
                    .email(signupDTO.getEmail())
                    .password(signupDTO.getPassword())
                    .birth(signupDTO.getBirth())
                    .phone(signupDTO.getPhone())
                    .name(signupDTO.getName())
                    .businessId(signupDTO.getBusinessId())
                    .isApprove(false)
                    .build();
            member.addMemberRole(Role.BUSINESSPERSON);
        }
        return member;
    }

    void logout();
}
