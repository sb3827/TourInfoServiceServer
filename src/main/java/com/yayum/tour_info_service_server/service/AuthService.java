package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Objects;

public interface AuthService {
    String login(JwtRequestDTO requestDTO);
    Long signup(SignupDTO signupDTO);
    Boolean emailCheck(String email);
    String findEmail(String name, String phone);
    ResponseDTO changePassword(ChangeMemberDTO changeMemberDTO);
    ResponseDTO resetPassword(MemberDTO memberDTO);
    default Member dtoToEntity(SignupDTO signupDTO) {
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
}
