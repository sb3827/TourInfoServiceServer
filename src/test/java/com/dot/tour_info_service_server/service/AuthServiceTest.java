package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.MemberDTO;
import com.dot.tour_info_service_server.dto.ResponseDTO;
import com.dot.tour_info_service_server.dto.SignupRequestDTO;
import com.dot.tour_info_service_server.dto.ChangeMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
class AuthServiceTest {
    @Autowired
    AuthService authService;
    @Test
    // member 등록 test code
    public void registMemberTest() {
        authService.signup(SignupRequestDTO.builder()
                        .email("email@email.com")
                        .password("1234")
                        .birth(LocalDate.now())
                        .phone("010-1111-2222")
                        .name("희범")
                        .role("MEMBER")
                .build());
    }

    @Test
    // business 등록 test code
    public void registBusinessTest() {
        authService.signup(SignupRequestDTO.builder()
                .email("email@email.com")
                .password("1234")
                .birth(LocalDate.now())
                .phone("010-1111-2222")
                .name("해창")
                .role("BUSINESSPERSON")
                .build());
    }

    @Test
    // 이메일 중복 test
    public void checkEmailTest() {
        log.info(authService.emailCheck("mmk2751@gmail.com"));
    }

    @Test
    public void findEmailTest() {
        log.info(authService.findEmail("희범1", "010-1234-5678"));
    }

    @Test
    public void changePasswordTest() {
        ResponseDTO result = authService.changePassword(ChangeMemberDTO.builder()
                        .email("member1@email.com")
                        .oldPassword("hb1234")
                        .newPassword("1234")
                .build());

        log.info(result);
    }

    @Test
    public void resetPasswordTest() {
        log.info(authService.resetPassword(MemberDTO.builder()
                        .email("member")
                .build()));
    }
}